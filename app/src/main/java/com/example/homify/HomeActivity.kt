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
import com.google.firebase.firestore.Query
import com.google.firebase.Timestamp
import java.util.Date
import java.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Upload drawable to Cloudinary when app opens
        val productList = listOf(
            Triple(
                R.drawable.wooden_sofa,
                "wooden_sofa.jpg",
                Product(
                    name = "Wooden Sofa",
                    description = "Comfortable wooden sofa for living room.",
                    price = "299.9",
                    details = "Material: Sheesham Wood\nColor: Walnut\n3-Seater",
                    onSale = true,
                    category="sofa",
                    discountPercentage = 20
                )
            ),
                Triple(
                    R.drawable.sofa2,
                    "sofa2.jpg",
                    Product(
                        name = "Modern 3-Seater Sofa",
                        description = "Stylish and modern 3-seater fabric sofa.",
                        price = "499.99",
                        details = "Material: Linen\nColor: Gray\nSize: 84 inch",
                        onSale = true,
                        category = "sofa",
                        discountPercentage = 20
                    )
                ),
        Triple(
            R.drawable.sofa3,
            "sofa3.jpg",
            Product(
                name = "Leather Recliner Sofa",
                description = "Premium recliner sofa with plush cushions.",
                price = "799.99",
                details = "Material: Leather\nColor: Brown\nSeating: 3",
                onSale = false,
                category = "sofa",
                discountPercentage = 0
            )
        ),
        Triple(
            R.drawable.cbsofa4,
            "cbsofa4.jpg",
            Product(
                name = "Convertible Sofa Bed",
                description = "Sofa that easily converts into a bed.",
                price = "399.00",
                details = "Color: Navy Blue\nSize: Queen\nMaterial: Microfiber",
                onSale = true,
                category = "sofa",
                discountPercentage = 25
            )
        ),
        Triple(
            R.drawable.chestersofa5,
            "chestersofa5.jpg",
            Product(
                name = "Classic Chesterfield Sofa",
                description = "Tufted design classic chesterfield.",
                price = "649.50",
                details = "Material: Velvet\nColor: Emerald Green\nStyle: Vintage",
                onSale = false,
                category = "sofa",
                discountPercentage = 0
            )
        ),
        Triple(
            R.drawable.lsofa6,
            "lsofa6.jpg",
            Product(
                name = "L-Shaped Corner Sofa",
                description = "Spacious L-shape sectional with cushions.",
                price = "699.90",
                details = "Material: Fabric\nColor: Light Grey\nSeating: 6",
                onSale = true,
                category = "sofa",
                discountPercentage = 15
            )
        ),
        Triple(
            R.drawable.futonsofa7,
            "futonsofa7.jpg",
            Product(
                name = "Futon Sofa",
                description = "Compact futon for studio or guest room.",
                price = "299.99",
                details = "Color: Charcoal\nFolds flat\nFrame: Metal",
                onSale = true,
                category = "sofa",
                discountPercentage = 10
            )
        ),
        Triple(
            R.drawable.woodbasesofa8,
            "woodbasesofa8.jpg",
            Product(
                name = "Minimalist Wooden Sofa",
                description = "Modern sofa with wooden base frame.",
                price = "459.00",
                details = "Material: Sheesham Wood\nColor: Beige\n3-Seater",
                onSale = false,
                category = "sofa",
                discountPercentage = 0
            )
        ),
        Triple(
            R.drawable.floralsofa9,
            "floralsofa9.jpg",
            Product(
                name = "Floral Fabric Sofa",
                description = "Bright floral patterned 2-seater sofa.",
                price = "379.00",
                details = "Material: Cotton\nColor: Multicolor",
                onSale = true,
                category = "sofa",
                discountPercentage = 10
            )
        ),
        Triple(
            R.drawable.velvetsofa10,
            "velvetsofa10.jpg",
            Product(
                name = "Luxury Velvet Sofa",
                description = "Elegant velvet sofa with gold legs.",
                price = "899.99",
                details = "Material: Velvet\nColor: Royal Blue\n3-Seater",
                onSale = true,
                category = "sofa",
                discountPercentage = 30
            )
        ),
        Triple(
            R.drawable.scandinaviansofa11,
            "scandinaviansofa11.jpg",
            Product(
                name = "Scandinavian Sofa",
                description = "Clean and simple Scandinavian design.",
                price = "549.99",
                details = "Material: Linen Blend\nColor: Light Cream",
                onSale = false,
                category = "sofa",
                discountPercentage = 0
            )
        ),
        Triple(
            R.drawable.recliningsofa12,
            "recliningsofa12.jpg",
            Product(
                name = "Reclining Sectional Sofa",
                description = "Multi-seat recliner with storage console.",
                price = "999.00",
                details = "Color: Dark Brown\nCup Holders: Yes",
                onSale = true,
                category = "sofa",
                discountPercentage = 25
            )
        ),
        Triple(
            R.drawable.compactsofa13,
            "compactsofa13.jpg",
            Product(
                name = "Compact City Sofa",
                description = "Small 2-seater perfect for apartments.",
                price = "299.99",
                details = "Material: Faux Leather\nColor: Black",
                onSale = false,
                category = "sofa",
                discountPercentage = 0
            )
        ),
        Triple(
            R.drawable.sheeshmsofa14,
            "sheeshmsofa14.jpg",
            Product(
                name = "Sheesham Wood Sofa",
                description = "Handcrafted Sheesham sofa with cushions.",
                price = "549.00",
                details = "Frame: Wood\nColor: Walnut\nCushions: Off-white",
                onSale = true,
                category = "sofa",
                discountPercentage = 15
            )
        ),
        Triple(
            R.drawable.tuftedsofa15,
            "tuftedsofa15.jpg",
            Product(
                name = "Grey Tufted Sofa",
                description = "Chic grey sofa with button tufting.",
                price = "479.00",
                details = "Material: Fabric\nFrame: Hardwood",
                onSale = true,
                category = "sofa",
                discountPercentage = 20
            )
        ),
        Triple(
            R.drawable.bohusofa16,
            "bohusofa16.jpg",
            Product(
                name = "Boho Sofa",
                description = "Bohemian style with colorful cushions.",
                price = "599.99",
                details = "Material: Cotton Blend\nColor: Mixed",
                onSale = false,
                category = "sofa",
                discountPercentage = 0
            )
        ),
            //chairs
            Triple(
                R.drawable.wingbackchair1,
                "wingbackchair1.jpg",
                Product(
                    name = "Wingback Armchair",
                    description = "Elegant high-back chair with flared arms.",
                    price = "249.99",
                    details = "Material: Velvet\nColor: Blue\nLegs: Wood",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 20
                )
            ),
            Triple(
                R.drawable.rocking_chair,
                "rocking_chair.jpg",
                Product(
                    name = "Rocking Chair",
                    description = "Classic wooden rocking chair.",
                    price = "199.00",
                    details = "Material: Teak Wood\nColor: Brown",
                    onSale = false,
                    category = "chair",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.ergonomic_office_chair,
                "ergonomic_office_chair.jpg",
                Product(
                    name = "Ergonomic Office Chair",
                    description = "Comfortable chair with lumbar support.",
                    price = "299.00",
                    details = "Material: Mesh + Plastic\nColor: Black",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 15
                )
            ),
            Triple(
                R.drawable.accent_lounge_chair,
                "accent_lounge_chair.jpg",
                Product(
                    name = "Accent Lounge Chair",
                    description = "Low-profile chair with curved back.",
                    price = "189.99",
                    details = "Material: Fabric\nColor: Olive",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.beige_leather_recliner,
                "beige_leather_recliner.jpg",
                Product(
                    name = "Beige Leather Recliner",
                    description = "Reclining armchair designed for maximum comfort in your TV room or lounge.",
                    price = "359.99",
                    details = "Style: Contemporary\nRoom: Living Room\nMaterial: Leatherette\nColor: Beige",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 25
                )
            ),
            Triple(
                R.drawable.modern_dining_chair_set,
                "modern_dining_chair_set.jpg",
                Product(
                    name = "Modern Dining Chair Set (2)",
                    description = "Upholstered high-back chairs for stylish dining spaces.",
                    price = "199.00",
                    details = "Style: Modern Dining\nRoom: Dining Room\nMaterial: Fabric\nColor: Soft Gray",
                    onSale = false,
                    category = "chair",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.lounge_egg_chair,
                "lounge_egg_chair.jpg",
                Product(
                    name = "Lounge Egg Chair",
                    description = "Iconic designer chair perfect for statement seating or cozy nooks.",
                    price = "699.00",
                    details = "Style: Scandinavian\nRoom: Living Room or Studio\nMaterial: Fabric + Steel\nColor: White",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 30
                )
            ),
            Triple(
                R.drawable.sheesham_lounge_chair,
                "sheesham_lounge_chair.jpg",
                Product(
                    name = "Sheesham Lounge Chair",
                    description = "Handcrafted Indian wood chair with traditional woven cane seat.",
                    price = "159.00",
                    details = "Style: Ethnic/Boho\nRoom: Living Room\nMaterial: Sheesham Wood + Cane\nColor: Walnut Finish",
                    onSale = false,
                    category = "chair",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.swivel_counter_stool,
                "swivel_counter_stool.jpg",
                Product(
                    name = "Swivel Counter Stool",
                    description = "Height-adjustable bar stool ideal for kitchen islands or home bars.",
                    price = "129.99",
                    details = "Style: Industrial\nRoom: Kitchen or Bar\nMaterial: Metal + Leather\nColor: Matte Black",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.plush_bean_bag_chair,
                "plush_bean_bag_chair.jpg",
                Product(
                    name = "Plush Bean Bag Chair",
                    description = "Soft and supportive bean bag, great for teen bedrooms or creative spaces.",
                    price = "79.99",
                    details = "Style: Casual/Youth\nRoom: Bedroom or Lounge\nMaterial: Microfiber\nColor: Teal Blue",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 20
                )
            ),
            Triple(
                R.drawable.compact_study_chair,
                "compact_study_chair.jpg",
                Product(
                    name = "Compact Study Chair",
                    description = "Ergonomic chair designed for focus and back support in study areas.",
                    price = "109.00",
                    details = "Style: Functional\nRoom: Study Room\nMaterial: Plastic + Cushion\nColor: White",
                    onSale = false,
                    category = "chair",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.traditional_armrest_chair,
                "traditional_armrest_chair.jpg",
                Product(
                    name = "Traditional Armrest Chair",
                    description = "Sturdy wooden chair with a colonial-style silhouette and rich finish.",
                    price = "179.99",
                    details = "Style: Colonial\nRoom: Living Room\nMaterial: Sheesham Wood\nColor: Mahogany",
                    onSale = false,
                    category = "chair",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.kids_activity_chair,
                "kids_activity_chair.jpg",
                Product(
                    name = "Kids Activity Chair",
                    description = "Bright, safe plastic chair designed for toddlers' learning corners.",
                    price = "49.00",
                    details = "Style: Educational\nRoom: Kids Room\nMaterial: Plastic\nColor: Red & Blue",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.luxury_lounge_chair_ottoman,
                "luxury_lounge_chair_ottoman.jpg",
                Product(
                    name = "Luxury Lounge Chair & Ottoman",
                    description = "Designer lounge chair with matching footrest for high-end interiors.",
                    price = "899.00",
                    details = "Style: Modern Luxury\nRoom: Lounge or Office\nMaterial: Faux Leather\nColor: Espresso Brown",
                    onSale = true,
                    category = "chair",
                    discountPercentage = 30
                )
            ),
            //lamps
            Triple(
                R.drawable.tripod_floor_lamp,
                "tripod_floor_lamp.jpg",
                Product(
                    name = "Tripod Floor Lamp",
                    description = "Modern floor lamp with wooden tripod base, ideal for contemporary living rooms.",
                    price = "149.99",
                    details = "Style: Modern Minimalist\nRoom: Living Room\nHeight: 5 ft\nBulb: E27\nColor: Beige",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 15
                )
            ),
            Triple(
                R.drawable.industrial_desk_lamp,
                "industrial_desk_lamp.jpg",
                Product(
                    name = "Industrial Desk Lamp",
                    description = "Retro-style desk lamp with metal frame for loft or workspace decor.",
                    price = "89.99",
                    details = "Style: Industrial\nRoom: Home Office\nMaterial: Iron\nColor: Black",
                    onSale = false,
                    category = "lamp",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.bedside_table_lamp,
                "bedside_table_lamp.jpg",
                Product(
                    name = "Bedside Table Lamp",
                    description = "Soft-glow ceramic lamp for cozy bedroom nightstands.",
                    price = "49.00",
                    details = "Style: Classic\nRoom: Bedroom\nMaterial: Ceramic\nShade: Fabric\nColor: White",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.chandelier_pendant_lamp,
                "chandelier_pendant_lamp.jpg",
                Product(
                    name = "Chandelier Pendant Lamp",
                    description = "Luxurious hanging chandelier to elevate dining or entry spaces.",
                    price = "299.00",
                    details = "Style: Luxury\nRoom: Dining Room or Foyer\nCrystal: Yes\nLights: 5 Bulbs",
                    onSale = false,
                    category = "lamp",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.led_wall_lamp,
                "led_wall_lamp.jpg",
                Product(
                    name = "LED Wall Lamp",
                    description = "Wall-mounted LED lamp offering soft ambient lighting.",
                    price = "59.99",
                    details = "Style: Modern\nRoom: Bedroom or Hallway\nMaterial: Aluminum\nColor: Warm White",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 20
                )
            ),
            Triple(
                R.drawable.adjustable_reading_lamp,
                "adjustable_reading_lamp.jpg",
                Product(
                    name = "Adjustable Reading Lamp",
                    description = "Clip-on reading light with a flexible neck for focused lighting.",
                    price = "39.99",
                    details = "Style: Functional\nRoom: Study or Bedside\nType: Clip-on\nBrightness: 3-Level",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 15
                )
            ),

            Triple(
                R.drawable.vintage_brass_lamp,
                "vintage_brass_lamp.jpg",
                Product(
                    name = "Vintage Brass Table Lamp",
                    description = "Old-school brass table lamp with elegant golden finish.",
                    price = "99.00",
                    details = "Style: Vintage\nRoom: Study or Living Room\nMaterial: Brass\nColor: Gold",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 25
                )
            ),
            Triple(
                R.drawable.ceiling_spotlights,
                "ceiling_spotlights.jpg",
                Product(
                    name = "Ceiling Spotlights",
                    description = "Adjustable ceiling spotlight set for modern interiors.",
                    price = "129.00",
                    details = "Style: Modern Industrial\nRoom: Living Room or Kitchen\nMaterial: Steel\nLights: 3 Heads",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.rgb_smart_lamp,
                "rgb_smart_lamp.jpg",
                Product(
                    name = "RGB Smart Lamp",
                    description = "App & voice-controlled smart lamp with color-changing modes.",
                    price = "79.00",
                    details = "Style: Tech-Enhanced\nRoom: Bedroom or Gaming Setup\nControl: App + Voice\nModes: 16 Colors",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 30
                )
            ),
            Triple(
                R.drawable.rustic_lantern_lamp,
                "rustic_lantern_lamp.jpg",
                Product(
                    name = "Rustic Lantern Lamp",
                    description = "Vintage-style lantern lamp perfect for cozy decor settings.",
                    price = "59.00",
                    details = "Style: Rustic\nRoom: Living Room or Entryway\nBattery: 2xAA\nMaterial: Metal",
                    onSale = false,
                    category = "lamp",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.kids_moon_night_lamp,
                "kids_moon_night_lamp.jpg",
                Product(
                    name = "Kids Moon Night Lamp",
                    description = "Cute moon-shaped night light for kids' bedrooms.",
                    price = "39.00",
                    details = "Style: Playful\nRoom: Kids Room\nShape: Moon\nMaterial: Plastic",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 15
                )
            ),
            Triple(
                R.drawable.swing_arm_wall_lamp,
                "swing_arm_wall_lamp.jpg",
                Product(
                    name = "Swing Arm Wall Lamp",
                    description = "Wall-mounted reading lamp with flexible swing arm.",
                    price = "69.00",
                    details = "Style: Functional Modern\nRoom: Bedroom or Study\nMaterial: Steel\nColor: Matte Black",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.crystal_table_lamp,
                "crystal_table_lamp.jpg",
                Product(
                    name = "Crystal Table Lamp",
                    description = "Elegant lamp with a shimmering crystal base and white shade.",
                    price = "149.00",
                    details = "Style: Glam\nRoom: Bedroom or Lounge\nShade: White\nMaterial: Glass + Metal",
                    onSale = false,
                    category = "lamp",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.rechargeable_led_lamp,
                "rechargeable_led_lamp.jpg",
                Product(
                    name = "Rechargeable LED Lamp",
                    description = "Portable LED lamp with adjustable brightness for any room.",
                    price = "45.00",
                    details = "Style: Portable\nRoom: Bedroom or Office\nUSB Charge: Yes\nBrightness: Adjustable",
                    onSale = true,
                    category = "lamp",
                    discountPercentage = 20
                )
            ),

            Triple(
                R.drawable.wardrobe_2_door,
                "wardrobe_2_door.jpg",
                Product(
                    name = "2-Door Wardrobe",
                    description = "Compact wardrobe with hanging space and minimalist design.",
                    price = "299.99",
                    details = "Style: Contemporary\nRoom: Bedroom\nMaterial: MDF\nColor: Wenge\nShelves: 2",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.sliding_door_cupboard,
                "sliding_door_cupboard.jpg",
                Product(
                    name = "Sliding Door Cupboard",
                    description = "Glossy-finish sliding wardrobe perfect for modern interiors.",
                    price = "599.00",
                    details = "Style: Modern\nRoom: Bedroom\nDoors: 2 Sliding\nFinish: Glossy",
                    onSale = false,
                    category = "cupboard",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.bookshelf_cupboard,
                "bookshelf_cupboard.jpg",
                Product(
                    name = "Bookshelf Cupboard",
                    description = "Tall storage unit with shelves and glass-panel doors.",
                    price = "249.00",
                    details = "Style: Transitional\nRoom: Study or Living Room\nMaterial: Engineered Wood\nColor: Walnut",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 20
                )
            ),
            Triple(
                R.drawable.kitchen_storage_cabinet,
                "kitchen_storage_cabinet.jpg",
                Product(
                    name = "Kitchen Storage Cabinet",
                    description = "Tall pantry-style cupboard for kitchen essentials.",
                    price = "199.00",
                    details = "Style: Functional\nRoom: Kitchen\nMaterial: MDF\nColor: White",
                    onSale = false,
                    category = "cupboard",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.wall_mounted_cupboard,
                "wall_mounted_cupboard.jpg",
                Product(
                    name = "Wall-Mounted Cupboard",
                    description = "Compact wall cabinet ideal for bathrooms or kitchens.",
                    price = "149.00",
                    details = "Style: Utility\nRoom: Kitchen or Bathroom\nColor: Brown\nShelves: 2",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 15
                )
            ),
            Triple(
                R.drawable.metal_utility_cupboard,
                "metal_utility_cupboard.jpg",
                Product(
                    name = "Metal Utility Cupboard",
                    description = "Durable lockable cabinet for garage or utility spaces.",
                    price = "329.00",
                    details = "Style: Industrial\nRoom: Utility Room or Garage\nMaterial: Metal\nShelves: 5\nColor: Grey",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 25
                )
            ),
            Triple(
                R.drawable.bathroom_storage_cupboard,
                "bathroom_storage_cupboard.jpg",
                Product(
                    name = "Bathroom Storage Cupboard",
                    description = "Compact white cabinet for storing toiletries and towels.",
                    price = "89.00",
                    details = "Style: Minimal\nRoom: Bathroom\nMaterial: Plastic + Wood\nColor: White",
                    onSale = false,
                    category = "cupboard",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.wardrobe_4_door,
                "wardrobe_4_door.jpg",
                Product(
                    name = "4-Door Wardrobe",
                    description = "Spacious wardrobe with drawers and multiple compartments.",
                    price = "799.99",
                    details = "Style: Contemporary\nRoom: Master Bedroom\nColor: Espresso\nMaterial: Engineered Wood",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 30
                )
            ),
            Triple(
                R.drawable.shoe_storage_cupboard,
                "shoe_storage_cupboard.jpg",
                Product(
                    name = "Shoe Storage Cupboard",
                    description = "Space-saving cabinet to neatly organize up to 15 pairs.",
                    price = "159.00",
                    details = "Style: Modern Utility\nRoom: Entryway or Closet\nColor: Light Oak\nCapacity: 15 Pairs",
                    onSale = false,
                    category = "cupboard",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.mirrored_wardrobe_cupboard,
                "mirrored_wardrobe_cupboard.jpg",
                Product(
                    name = "Mirrored Cupboard",
                    description = "Full-length mirrored wardrobe with internal shelves and rod.",
                    price = "449.99",
                    details = "Style: Modern\nRoom: Bedroom or Dressing Room\nColor: Walnut\nShelves + Hanging Rod",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 20
                )
            ),
            Triple(
                R.drawable.wooden_storage_cabinet,
                "wooden_storage_cabinet.jpg",
                Product(
                    name = "Wooden Storage Cabinet",
                    description = "Classic Sheesham wood cabinet with rich natural grain.",
                    price = "229.00",
                    details = "Style: Traditional\nRoom: Living Room or Bedroom\nMaterial: Sheesham Wood\nColor: Natural",
                    onSale = false,
                    category = "cupboard",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.kids_closet_cupboard,
                "kids_closet_cupboard.jpg",
                Product(
                    name = "Children's Closet",
                    description = "Colorful and safe closet for kids' clothing and toys.",
                    price = "179.00",
                    details = "Style: Playful\nRoom: Kids Room\nColor: Pink/Blue\nMaterial: Plastic",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 10
                )
            ),
            Triple(
                R.drawable.glass_door_showcase,
                "glass_door_showcase.jpg",
                Product(
                    name = "Glass Door Cupboard",
                    description = "Showcase cupboard with black frame and glass doors.",
                    price = "319.00",
                    details = "Style: Display\nRoom: Living Room or Dining\nMaterial: MDF + Glass\nColor: Black",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 15
                )
            ),
            Triple(
                R.drawable.office_storage_cupboard,
                "office_storage_cupboard.jpg",
                Product(
                    name = "Office Storage Cupboard",
                    description = "Functional white cabinet for documents and supplies.",
                    price = "199.00",
                    details = "Style: Functional Office\nRoom: Study or Workspace\nMaterial: MDF\nShelves: 4\nColor: White",
                    onSale = false,
                    category = "cupboard",
                    discountPercentage = 0
                )
            ),
            Triple(
                R.drawable.utility_room_cupboard,
                "utility_room_cupboard.jpg",
                Product(
                    name = "Utility Cupboard",
                    description = "Versatile cupboard that fits in kitchens, offices, or bedrooms.",
                    price = "189.00",
                    details = "Style: Multi-Purpose\nRoom: Any Room\nMaterial: Engineered Wood\nColor: Maple",
                    onSale = true,
                    category = "cupboard",
                    discountPercentage = 15
                )
            ),
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
                                "details" to Product.details,
                                "category" to Product.category,
                                "onSale" to true, // <-- Add this
                                "viewCount" to 0,  // Initialize view count
                                "createdAt" to FieldValue.serverTimestamp(),  // Add timestamp
                                "discountPercentage" to 0
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
    fun CategoryScreen(categoryName: String, navController: NavHostController) {
        val firestore = FirebaseFirestore.getInstance()
        val products = remember { mutableStateOf<List<Product>>(emptyList()) }
        val isLoading = remember { mutableStateOf(true) }

        LaunchedEffect(categoryName) {
            try {
                val query = if (categoryName == "All") {
                    firestore.collection("products")
                } else {
                    firestore.collection("products")
                        .whereEqualTo("category", categoryName.lowercase())
                }

                query.get().addOnSuccessListener { snapshot ->
                    products.value = snapshot.documents.map { doc ->
                        doc.toObject<Product>()!!.copy(id = doc.id)
                    }
                    isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("CategoryScreen", "Error loading products", e)
                isLoading.value = false
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("$categoryName Collection") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    }
                )
            }
        ) { padding ->
            if (isLoading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (products.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No products found in this category")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products.value) { product ->
                        ProductCardCompact(product = product, navController = navController)
                    }
                }
            }
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
                composable("category/{categoryName}") { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                    CategoryScreen(categoryName = categoryName, navController = navController)
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
        val onSale: Boolean = false,             // New
        val discountPercentage: Int = 0,
        val viewCount: Int = 0,  // For popularity tracking
        val createdAt: Timestamp? = null,  // For "Newest" filter
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
        var selectedFilter by remember { mutableStateOf("All") }
        val filteredProducts = remember(products.value, searchQuery, selectedFilter) {
            val thirtyDaysAgo = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -30)
            }.time

            val baseList = if (searchQuery.isEmpty()) {
                products.value
            } else {
                products.value.filter { product ->
                    product.name.contains(searchQuery, ignoreCase = true) ||
                            product.description.contains(searchQuery, ignoreCase = true)
                }
            }

            when (selectedFilter) {
                "Newest" -> baseList.filter { product ->
                    product.createdAt?.toDate()?.after(thirtyDaysAgo) ?: false
                }.sortedByDescending { it.createdAt?.toDate() }
                "Popular" -> baseList.sortedByDescending { it.viewCount }
                "Bedroom" -> baseList.filter { it.category.equals("bedroom", ignoreCase = true) }
                else -> baseList // "All"
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
                    .orderBy("createdAt", Query.Direction.DESCENDING)
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
            }
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

            }

            // Category Items
            val categories = listOf(
                "Sofa" to Icons.Default.Weekend,
                "Chair" to Icons.Default.Chair,
                "Lamp" to Icons.Default.Lightbulb,
                "Cupboard" to Icons.Default.Kitchen
            )

            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { (name, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(80.dp)
                            .clickable {
                                navController.navigate("category/$name")
                            }
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
                        selected = filter == selectedFilter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        ),
                        border = when (filter) {
                            selectedFilter -> BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            else -> null
                        }
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
                // Increment view count when product is viewed
                FirebaseFirestore.getInstance()
                    .collection("products")
                    .document(productId)
                    .update("viewCount", FieldValue.increment(1))

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

