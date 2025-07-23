package com.example.homify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import androidx.compose.ui.platform.LocalContext
import kotlin.jvm.java
import com.google.firebase.firestore.FieldValue
import androidx.compose.material.Card
import android.content.Context
import java.io.File
import java.io.FileOutputStream
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*

@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Upload drawable to Cloudinary when app opens
        val productList = listOf(
            Triple(R.drawable.wooden_sofa, "wooden_sofa.jpg", Product("Wooden Sofa", "Comfortable wooden sofa for living room.", "299.9", "Material: Sheesham Wood\nColor: Walnut\n3-Seater")),
            Triple(R.drawable.dining_table, "dining_table.jpg", Product("Dining Table", "Spacious dining table for family meals.", "499.0", "Material: Teak Wood\nSeats: 6")),
            Triple(R.drawable.kitchen_chair, "kitchen_chair.jpg", Product("Kitchen Chair", "Stylish kitchen chair.", "120.5", "Color: Black\nMaterial: Metal")),
            Triple(R.drawable.book_shelf, "shelf_unit.jpg", Product("Shelf Unit", "Modern wall-mounted shelf.", "89.99", "Color: White\nMaterial: MDF")),
        )

        val db = FirebaseFirestore.getInstance()

        // ✅ Place this FOR EACH block here (fixed version)
        productList.forEach { (drawableId, fileName, Product) ->
            db.collection("products")
                .whereEqualTo("name", Product.name)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        val file = drawableToFile(this, drawableId, fileName)

                        uploadImageToCloudinary(file, this) { url ->
                            Log.d("Cloudinary", "Uploaded Image URL: $url")

                            val product = hashMapOf(
                                "name" to Product.name,
                                "description" to Product.description,
                                "price" to Product.price,
                                "imageUrl" to url,
                                "details" to Product.details
                            )

                            db.collection("products")
                                .add(product)
                                .addOnSuccessListener {
                                    Log.d("Cloudinary", "✅ ${Product.name} added to Firestore")
                                }
                                .addOnFailureListener {
                                    Log.e("Cloudinary", "❌ Failed to add ${Product.name}", it)
                                }
                        }
                    } else {
                        Log.d("Cloudinary", "⚠️ Product '${Product.name}' already exists, skipping")
                    }
                }
                .addOnFailureListener {
                    Log.e("Cloudinary", "❌ Error checking ${Product.name}", it)
                }
        }
            setContent {
                FurnitureApp()
            }
        }


    @Composable
    fun FurnitureApp() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("home") { HomeScreen(navController) }
                composable("detail/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId") ?: ""
                    ProductDetailScreen(productId = productId, navController = navController)
                }
                composable("search") { SearchScreen() }
                composable("cart") { CartScreen() }
                composable("favorites") { FavoritesScreen() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = navController.currentDestination?.route == "home",
                onClick = { navController.navigate("home") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                label = { Text("Search") },
                selected = navController.currentDestination?.route == "search",
                onClick = { navController.navigate("search") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                label = { Text("Cart") },
                selected = navController.currentDestination?.route == "cart",
                onClick = { navController.navigate("cart") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                label = { Text("Favorites") },
                selected = navController.currentDestination?.route == "favorites",
                onClick = { navController.navigate("favorites") }
            )
        }
    }

    data class Product(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val price: String = "",
        val imageUrl: String = "",
        val category: String = "",
        val details: String = ""
    )
    // for real timer
    @Composable
    fun FlashSaleTimer() {
        var remainingTime by remember { mutableStateOf(2 * 3600 + 12 * 60 + 56) } // 02:12:56 in seconds

        LaunchedEffect(Unit) {
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime--
            }
        }

        val hours = remainingTime / 3600
        val minutes = (remainingTime % 3600) / 60
        val seconds = remainingTime % 60

        Row(verticalAlignment = Alignment.CenterVertically) {
            listOf(hours, minutes, seconds).forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .background(Color.Red.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "%02d".format(value),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                if (index < 2) {
                    Text(
                        text = ":",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        color = Color.Red
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(navController: NavHostController) {
        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val products = remember { mutableStateOf<List<Product>>(emptyList()) }
        val isLoading = remember { mutableStateOf(true) }
        val error = remember { mutableStateOf<String?>(null) }
        var searchQuery by remember { mutableStateOf("") }
        val filteredProducts = remember(products.value, searchQuery) {
            if (searchQuery.isEmpty()) products.value
            else products.value.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true)
            }
        }
        // Check authentication
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Please log in to continue")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* Navigate to login */ }) {
                    Text("Go to Login")
                }
            }
            return
        }

        // Fetch data
        LaunchedEffect(Unit) {
            try {
                firestore.collection("products")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        products.value = snapshot.documents.map { doc ->
                            doc.toObject<Product>()!!.copy(id = doc.id)
                        }
                        isLoading.value = false
                    }
                    .addOnFailureListener { e ->
                        error.value = "Error loading products: ${e.message}"
                        isLoading.value = false
                    }
            } catch (e: Exception) {
                error.value = "Unexpected error: ${e.message}"
                isLoading.value = false
            }
        }

        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        error.value?.let { errorMessage ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        isLoading.value = true
                        error.value = null
                    }) {
                        Text("Retry")
                    }
                }
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Location and Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rawalpindi, PAKISTAN",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    placeholder = { Text("Search Furniture") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                        focusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
                    )
                )
            }

            // New Collection Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "New Collection",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Discount 50% for the first transaction",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* Handle shop now */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Shop Now")
                    }
                }
            }

            // Category Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "See All",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Category Items
            val categories = listOf(
                Pair(Icons.Default.Weekend, "Sofa"),  // Using Weekend icon for Sofa
                Pair(Icons.Default.Chair, "Chair"),
                Pair(Icons.Default.Lightbulb, "Lamp"),
                Pair(Icons.Default.Kitchen, "Cupboard")  // Using Kitchen icon for Cupboard
            )

// Category Row
            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { (icon, name) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(80.dp)
                            .clickable { /* Handle category click */ }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = name,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            // Flash Sale Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Flash Sale",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                FlashSaleTimer()


            }

            // Filter Chips
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Newest", "Popular", "Bedroom").forEach { filter ->
                    FilterChip(
                        selected = filter == "All",
                        onClick = { /* Handle filter change */ },
                        label = { Text(filter) }
                    )
                }
            }

            // Products Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredProducts) { product -> // <-- Changed from products.value
                    ProductCardCompact(product = product, navController = navController)
                }
            }
        }
    }

    @Composable
    fun ProductCardCompact(product: Product, navController: NavHostController) {
        val context = LocalContext.current
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut()
        ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("detail/${product.id}") },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {
                // Product image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (product.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = product.imageUrl,
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("Image not available")
                    }
                }
            }
                // Product details
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }

    data class CartItem(
        val id: String = "",
        val productId: String = "",
        val name: String = "",
        val price: Double = 0.0,
        val quantity: Int = 1,
        val imageUrl: String = "",
        val userId: String = ""
    )

    @Composable
    fun ProductDetailScreen(productId: String, navController: NavHostController) {
        val product = remember { mutableStateOf<Product?>(null) }
        val isLoading = remember { mutableStateOf(true) }

        LaunchedEffect(productId) {
            try {
                val doc = FirebaseFirestore.getInstance()
                    .collection("products")
                    .document(productId)
                    .get()
                    .await()

                product.value = doc.toObject<Product>()?.copy(id = doc.id)
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading.value = false
            }
        }

        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        val currentProduct = product.value ?: return

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (currentProduct.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = currentProduct.imageUrl,
                        contentDescription = currentProduct.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Image not available")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentProduct.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentProduct.description,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = currentProduct.price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = currentProduct.details,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current

            Button(
                onClick = {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId == null) {
                        Toast.makeText(context, "Please log in to add to cart", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    val db = FirebaseFirestore.getInstance()
                    val cartRef = db.collection("carts")
                    val query = cartRef
                        .whereEqualTo("userId", userId)
                        .whereEqualTo("productId", currentProduct.id) // ✅ use currentProduct

                    query.get().addOnSuccessListener { result ->
                        if (!result.isEmpty) {
                            val doc = result.documents[0]
                            val existingQuantity = doc.getLong("quantity")?.toInt() ?: 1
                            cartRef.document(doc.id).update("quantity", existingQuantity + 1)
                        } else {
                            val priceAsDouble =
                                currentProduct.price.toDoubleOrNull() ?: 0.0 // ✅ Convert

                            val cartItem = hashMapOf(
                                "userId" to userId,
                                "productId" to currentProduct.id,
                                "name" to currentProduct.name,
                                "price" to priceAsDouble, // ✅ Store as number
                                "quantity" to 1,
                                "imageUrl" to currentProduct.imageUrl
                            )

                            cartRef.add(cartItem)
                        }

                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Add to Cart")
            }


        }
    }

    @Composable
    fun SearchScreen() {
        var query by remember { mutableStateOf("") }
        val allProducts = remember { mutableStateOf<List<Product>>(emptyList()) }
        val filteredProducts = remember(query, allProducts.value) {
            allProducts.value.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }

        val firestore = FirebaseFirestore.getInstance()

        LaunchedEffect(Unit) {
            firestore.collection("products")
                .get()
                .addOnSuccessListener { snapshot ->
                    allProducts.value = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Product::class.java)?.copy(id = doc.id)
                    }
                }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🔍 Search Bar
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search products...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
            )

            // 📋 Results List
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredProducts) { product ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp)) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(product.name, fontWeight = FontWeight.Bold)
                                Text("Price: ${product.price}")
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun CartScreen() {
        val context = LocalContext.current
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
        var total by remember { mutableStateOf(0.0) }

        LaunchedEffect(Unit) {
            val userId = auth.currentUser?.uid ?: return@LaunchedEffect

            db.collection("carts")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Toast.makeText(
                            context,
                            "Error loading cart: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addSnapshotListener
                    }

                    val items = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(CartItem::class.java)?.copy(id = doc.id)
                    } ?: emptyList()

                    cartItems = items
                    total = items.sumOf { it.price * it.quantity }
                }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onQuantityChange = { newQuantity ->
                            db.collection("carts").document(item.id)
                                .update("quantity", newQuantity)
                        },
                        onRemove = {
                            db.collection("carts").document(item.id).delete()
                        }
                    )
                }
            }

            Text(
                text = "Total: $${"%.2f".format(total)}",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Button(
                onClick = {
                    if (cartItems.isEmpty()) {
                        Toast.makeText(context, "Your cart is empty", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val userId = auth.currentUser?.uid ?: return@Button
                    val orderRef = db.collection("orders").document()

                    val order = hashMapOf(
                        "orderId" to orderRef.id,
                        "userId" to userId,
                        "items" to cartItems.map {
                            mapOf(
                                "productId" to it.productId,
                                "name" to it.name,
                                "price" to it.price,
                                "quantity" to it.quantity
                            )
                        },
                        "total" to total,
                        "timestamp" to FieldValue.serverTimestamp(),
                        "status" to "pending"
                    )

                    orderRef.set(order)
                        .addOnSuccessListener {
                            cartItems.forEach {
                                db.collection("carts").document(it.id).delete()
                            }
                            Toast.makeText(
                                context,
                                "Order placed successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error placing order", Toast.LENGTH_SHORT)
                                .show()
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Checkout")
            }
        }
    }

    @Composable
    fun CartItemCard(
        item: CartItem,
        onQuantityChange: (Int) -> Unit,
        onRemove: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(item.name, fontWeight = FontWeight.Bold)
                    Text("Price: $${item.price}")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = {
                            if (item.quantity > 1) onQuantityChange(item.quantity - 1)
                        }) {
                            Text("-")
                        }
                        Text("${item.quantity}", modifier = Modifier.padding(horizontal = 8.dp))
                        Button(onClick = {
                            onQuantityChange(item.quantity + 1)
                        }) {
                            Text("+")
                        }
                    }
                }

                Button(
                    onClick = onRemove, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Text("Remove")
                }
            }
        }
    }


    @Composable
    fun FavoritesScreen() {
        val context = LocalContext.current
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        var favorites by remember { mutableStateOf<List<Product>>(emptyList()) }

        // Load favorites from Firestore
        LaunchedEffect(Unit) {
            val userId = auth.currentUser?.uid ?: return@LaunchedEffect

            db.collection("favorites")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Toast.makeText(context, "Failed to load favorites", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    val favItems = snapshot?.documents?.mapNotNull { doc ->
                        Product(
                            id = doc.getString("productId") ?: "",
                            name = doc.getString("name") ?: "",
                            description = "",
                            price = doc.getDouble("price")?.toString() ?: "0.0",
                            imageUrl = doc.getString("imageUrl") ?: "",
                            details = ""
                        )
                    } ?: emptyList()

                    favorites = favItems
                }
        }

        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No favorites yet.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(favorites) { product ->
                    ProductCardCompact(product = product, navController = rememberNavController())
                }
            }
        }
    }
}
// ✅ Converts a drawable to a File
fun drawableToFile(context: Context, drawableId: Int, fileName: String): File {
    val file = File(context.cacheDir, fileName)
    val inputStream = context.resources.openRawResource(drawableId)
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()
    return file
}

