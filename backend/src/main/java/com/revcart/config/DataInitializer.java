package com.revcart.config;

import com.revcart.entity.DeliveryAgent;
import com.revcart.entity.Product;
import com.revcart.entity.User;
import com.revcart.entity.Coupon;
import com.revcart.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DeliveryAgentRepository deliveryAgentRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@revcart.com")) {
            User admin = new User("Admin", "admin@revcart.com", passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setPhone("9876543210");
            admin.setAddress("Admin Address");
            userRepository.save(admin);
        }

        if (deliveryAgentRepository.count() == 0) {
            DeliveryAgent agent = new DeliveryAgent();
            agent.setEmail("agent@revcart.com");
            agent.setPassword("agent123");
            agent.setName("John Delivery");
            agent.setPhone("9876543210");
            agent.setStatus(DeliveryAgent.Status.AVAILABLE);
            deliveryAgentRepository.save(agent);
        }

        // Always ensure we have products for demo
        if (productRepository.count() == 0) {
            System.out.println("[DataInitializer] No products found. Creating sample products...");
            initializeProducts();
            System.out.println("[DataInitializer] Created " + productRepository.count() + " products");
        } else {
            System.out.println("[DataInitializer] Found " + productRepository.count() + " products in database");
        }

        // Initialize sample coupons - always recreate for demo
        System.out.println("[DataInitializer] Current coupon count: " + couponRepository.count());
        if (couponRepository.count() == 0) {
            System.out.println("[DataInitializer] Creating sample coupons...");
            initializeCoupons();
            System.out.println("[DataInitializer] Created " + couponRepository.count() + " coupons");
        } else {
            System.out.println("[DataInitializer] Coupons already exist");
        }
    }

    private void initializeProducts() {
        // Fruits
        productRepository.save(new Product("Fresh Apples", "fruits", new BigDecimal("120"), "1kg", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=300&h=200&fit=crop", "Fresh red apples"));
        productRepository.save(new Product("Bananas", "fruits", new BigDecimal("60"), "1kg", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=300&h=200&fit=crop", "Ripe yellow bananas"));
        productRepository.save(new Product("Oranges", "fruits", new BigDecimal("100"), "1kg", "https://images.unsplash.com/photo-1547514701-42782101795e?w=300&h=200&fit=crop", "Juicy oranges"));
        productRepository.save(new Product("Grapes", "fruits", new BigDecimal("150"), "500g", "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=300&h=200&fit=crop", "Sweet grapes"));
        productRepository.save(new Product("Strawberries", "fruits", new BigDecimal("200"), "250g", "https://images.unsplash.com/photo-1518635017498-87f514b751ba?w=300&h=200&fit=crop", "Fresh strawberries"));
        productRepository.save(new Product("Mangoes", "fruits", new BigDecimal("180"), "1kg", "https://www.metropolisindia.com/upgrade/blog/upload/25/05/benefits-of-mangoes1747828357.webp", "Sweet mangoes"));
        productRepository.save(new Product("Pineapple", "fruits", new BigDecimal("80"), "1 piece", "https://images.unsplash.com/photo-1589820296156-2454bb8a6ad1?w=300&h=200&fit=crop", "Fresh pineapple"));
        productRepository.save(new Product("Watermelon", "fruits", new BigDecimal("40"), "1kg", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=300&h=200&fit=crop", "Juicy watermelon"));

        // Vegetables
        productRepository.save(new Product("Carrots", "vegetables", new BigDecimal("40"), "500g", "https://images.unsplash.com/photo-1445282768818-728615cc910a?w=300&h=200&fit=crop", "Fresh orange carrots"));
        productRepository.save(new Product("Tomatoes", "vegetables", new BigDecimal("80"), "1kg", "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=300&h=200&fit=crop", "Fresh red tomatoes"));
        productRepository.save(new Product("Onions", "vegetables", new BigDecimal("30"), "1kg", "https://images.unsplash.com/photo-1508747703725-719777637510?w=300&h=200&fit=crop", "Fresh onions"));
        productRepository.save(new Product("Potatoes", "vegetables", new BigDecimal("25"), "1kg", "https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=300&h=200&fit=crop", "Fresh potatoes"));
        productRepository.save(new Product("Broccoli", "vegetables", new BigDecimal("60"), "500g", "https://images.unsplash.com/photo-1628773822503-930a7eaecf80?w=300&h=200&fit=crop", "Fresh broccoli"));
        productRepository.save(new Product("Spinach", "vegetables", new BigDecimal("35"), "250g", "https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=300&h=200&fit=crop", "Fresh spinach leaves"));
        productRepository.save(new Product("Bell Peppers", "vegetables", new BigDecimal("70"), "500g", "https://images.unsplash.com/photo-1563565375-f3fdfdbefa83?w=300&h=200&fit=crop", "Colorful bell peppers"));
        productRepository.save(new Product("Cauliflower", "vegetables", new BigDecimal("45"), "1 piece", "https://images.unsplash.com/photo-1594282486552-05b4d80fbb9f?w=300&h=200&fit=crop", "Fresh cauliflower"));

        // Dairy
        productRepository.save(new Product("Milk", "dairy", new BigDecimal("55"), "1L", "https://nutritionsource.hsph.harvard.edu/wp-content/uploads/2024/11/AdobeStock_354060824-1024x683.jpeg", "Fresh cow milk"));
        productRepository.save(new Product("Cheese", "dairy", new BigDecimal("200"), "250g", "https://images.unsplash.com/photo-1486297678162-eb2a19b0a32d?w=300&h=200&fit=crop", "Fresh cheese"));
        productRepository.save(new Product("Yogurt", "dairy", new BigDecimal("45"), "500g", "https://img.freepik.com/free-vector/realistic-vector-icon-illustration-strawberry-yoghurt-jar-with-spoon-full-yogurt-isolated_134830-2521.jpg?semt=ais_hybrid&w=740&q=80", "Greek yogurt"));
        productRepository.save(new Product("Butter", "dairy", new BigDecimal("120"), "200g", "https://images.unsplash.com/photo-1589985270826-4b7bb135bc9d?w=300&h=200&fit=crop", "Fresh butter"));
        productRepository.save(new Product("Cream", "dairy", new BigDecimal("80"), "200ml", "https://www.realsimple.com/thmb/WIQw_c6ePyPKkXAGrFVB5hvMN_A=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/make-sour-cream-2000-513d49b3aaba4708a67b19380cc32de6.jpg", "Heavy cream"));
        productRepository.save(new Product("Ice Cream", "dairy", new BigDecimal("150"), "500ml", "https://images.unsplash.com/photo-1567206563064-6f60f40a2b57?w=300&h=200&fit=crop", "Vanilla ice cream"));
        productRepository.save(new Product("Paneer", "dairy", new BigDecimal("180"), "250g", "https://chennaionlineshopping.in/image/cache/catalog/Products/panner/amul%20panner-800x800.jpg", "Fresh paneer"));
        productRepository.save(new Product("Ghee", "dairy", new BigDecimal("300"), "500ml", "https://ueirorganic.com/cdn/shop/files/purecowghee.jpg?v=1689066451", "Pure cow ghee"));

        // Bakery
        productRepository.save(new Product("Bread", "bakery", new BigDecimal("35"), "1 loaf", "https://assets.bonappetit.com/photos/5c62e4a3e81bbf522a9579ce/1:1/pass/milk-bread.jpg", "Whole wheat bread"));
        productRepository.save(new Product("Croissant", "bakery", new BigDecimal("25"), "1 piece", "https://sugargeekshow.com/wp-content/uploads/2022/11/croissants_featured.jpg", "Buttery croissant"));
        productRepository.save(new Product("Muffins", "bakery", new BigDecimal("80"), "4 pieces", "https://images.unsplash.com/photo-1607958996333-41aef7caefaa?w=300&h=200&fit=crop", "Blueberry muffins"));
        productRepository.save(new Product("Cookies", "bakery", new BigDecimal("60"), "6 pieces", "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=300&h=200&fit=crop", "Chocolate cookies"));
        productRepository.save(new Product("Donuts", "bakery", new BigDecimal("120"), "6 pieces", "https://images.unsplash.com/photo-1551024506-0bccd828d307?w=300&h=200&fit=crop", "Glazed donuts"));
        productRepository.save(new Product("Cake", "bakery", new BigDecimal("400"), "1 piece", "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=300&h=200&fit=crop", "Chocolate cake"));
        productRepository.save(new Product("Bagels", "bakery", new BigDecimal("90"), "4 pieces", "https://www.tasteofhome.com/wp-content/uploads/2025/01/Homemade-Bagels_EXPS_TOHD25_15702_ChristineMa_9.jpg", "Fresh bagels"));
        productRepository.save(new Product("Pastry", "bakery", new BigDecimal("50"), "1 piece", "https://krbakes.com/cdn/shop/articles/Top_10_Trending_Pastry_Cakes_You_Need_to_Try.webp?v=1739364407&width=1920", "Fruit pastry"));

        // Electronics
        productRepository.save(new Product("Smartphone", "electronics", new BigDecimal("15000"), "1 piece", "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=300&h=200&fit=crop", "Latest smartphone"));
        productRepository.save(new Product("Headphones", "electronics", new BigDecimal("2500"), "1 piece", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=200&fit=crop", "Wireless headphones"));
        productRepository.save(new Product("Laptop", "electronics", new BigDecimal("45000"), "1 piece", "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=300&h=200&fit=crop", "Gaming laptop"));
        productRepository.save(new Product("Smart Watch", "electronics", new BigDecimal("8000"), "1 piece", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=300&h=200&fit=crop", "Fitness smart watch"));
        productRepository.save(new Product("Tablet", "electronics", new BigDecimal("20000"), "1 piece", "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=300&h=200&fit=crop", "10-inch tablet"));
        productRepository.save(new Product("Power Bank", "electronics", new BigDecimal("1500"), "1 piece", "https://i03.appmifile.com/333_item_in/08/07/2025/9047b35e12fa25cb45fc93a824a29e87.jpg", "10000mAh power bank"));
        productRepository.save(new Product("Bluetooth Speaker", "electronics", new BigDecimal("3000"), "1 piece", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=300&h=200&fit=crop", "Portable speaker"));
        productRepository.save(new Product("Wireless Mouse", "electronics", new BigDecimal("800"), "1 piece", "https://m.media-amazon.com/images/I/51vMo-pHZ5L.jpg", "Ergonomic wireless mouse"));

        // Sports
        productRepository.save(new Product("Football", "sports", new BigDecimal("800"), "1 piece", "https://images.unsplash.com/photo-1486286701208-1d58e9338013?w=300&h=200&fit=crop", "Professional football"));
        productRepository.save(new Product("Cricket Bat", "sports", new BigDecimal("1200"), "1 piece", "https://dkpcricketonline.com/cdn/shop/collections/image_419d887e-bcd5-4469-9925-776dc84db52b.heic?v=1754925807&width=2400", "Professional cricket bat"));
        productRepository.save(new Product("Cricket Ball", "sports", new BigDecimal("300"), "1 piece", "https://nwscdn.com/media/catalog/product/cache/h400xw400/c/r/cricket-club-ball-family_1.jpg", "Leather cricket ball"));
        productRepository.save(new Product("Tennis Racket", "sports", new BigDecimal("1500"), "1 piece", "https://us.yonex.com/cdn/shop/files/CLP_Tennis_Ezone_D.jpg?v=1740757953&width=1500", "Professional tennis racket"));
        productRepository.save(new Product("Basketball", "sports", new BigDecimal("900"), "1 piece", "https://static.nbastore.in/resized/900X900/1180/wilson-nba-mens-drv-pro-basketball-brown-brown-68dc39e5a64de.jpg?format=webp", "Professional basketball"));
        productRepository.save(new Product("Badminton Racket", "sports", new BigDecimal("800"), "1 piece", "https://cdn.firstcry.com/education/2022/07/25185734/Essay-On-My-Favourite-Game-Badminton-10-Lines-Short-and-Long-Essay-For-Kids.jpg", "Lightweight badminton racket"));
        productRepository.save(new Product("Table Tennis Paddle", "sports", new BigDecimal("400"), "1 piece", "https://m.media-amazon.com/images/I/81OnewcSyTL.jpg", "Professional table tennis paddle"));
        productRepository.save(new Product("Volleyball", "sports", new BigDecimal("600"), "1 piece", "https://m.media-amazon.com/images/I/61pFab9tNeL.jpg", "Professional volleyball"));
        productRepository.save(new Product("Yoga Mat", "sports", new BigDecimal("600"), "1 piece", "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=300&h=200&fit=crop", "Premium yoga mat"));
        productRepository.save(new Product("Dumbbells", "sports", new BigDecimal("2000"), "1 pair", "https://www.vinexshop.com/Product-Images/Large/2150-Dumbbells-Iron.jpg", "Adjustable dumbbells"));
        productRepository.save(new Product("Swimming Goggles", "sports", new BigDecimal("350"), "1 piece", "https://rukminim2.flixcart.com/image/356/352/xif0q/goggle/r/i/s/-original-imahe3kahqp5zyfy.jpeg?q=90&crop=false", "Anti-fog swimming goggles"));
        productRepository.save(new Product("Boxing Gloves", "sports", new BigDecimal("1800"), "1 pair", "https://m.media-amazon.com/images/I/81MThv+hgeS.jpg", "Professional boxing gloves"));

        // Kids
        productRepository.save(new Product("Teddy Bear", "kids", new BigDecimal("500"), "1 piece", "https://tse1.mm.bing.net/th/id/OIP.IQUsCBaKM8Ox51lI1XH5BAHaFR?pid=Api&P=0&h=180", "Soft teddy bear"));
        productRepository.save(new Product("Building Blocks", "kids", new BigDecimal("800"), "1 set", "https://baybee.co.in/cdn/shop/files/71Z7Rwn2BGL._SL1500.jpg?v=1735995897", "Colorful building blocks"));
        productRepository.save(new Product("Puzzle Game", "kids", new BigDecimal("300"), "1 piece", "https://images.unsplash.com/photo-1601987177651-8edfe6c20009?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8cHV6emxlJTIwZ2FtZXxlbnwwfHwwfHx8MA%3D%3D", "Educational puzzle"));
        productRepository.save(new Product("Remote Car", "kids", new BigDecimal("1200"), "1 piece", "https://cdn-gnhif.nitrocdn.com/TVcQuMoyCLAXtNubQjipHiuZSBgcXCHY/assets/images/optimized/rev-b921d37/www.daddydrones.in/image/cache/catalog/HOSPEED/HS16351/FRONT/image0-500x500.jpeg", "RC racing car"));

        // Beauty
        productRepository.save(new Product("Face Cream", "beauty", new BigDecimal("450"), "50ml", "https://dr.rashel.in/cdn/shop/products/Vitamin_C_Face_Cream.jpg?v=1755964552", "Anti-aging face cream"));
        productRepository.save(new Product("Lipstick", "beauty", new BigDecimal("350"), "1 piece", "https://images-static.nykaa.com/media/catalog/product/b/5/b560771773602685189_2.png?tr=w-500", "Matte lipstick"));
        productRepository.save(new Product("Shampoo", "beauty", new BigDecimal("250"), "200ml", "https://barcodeprofessional.in/cdn/shop/files/01_7aaa4ca4-6c4e-44f7-816f-86b2f49489ef.jpg?v=1706353626", "Hair care shampoo"));
        productRepository.save(new Product("Perfume", "beauty", new BigDecimal("1500"), "100ml", "https://images.pexels.com/photos/1961791/pexels-photo-1961791.jpeg?cs=srgb&dl=pexels-valeriya-1961791.jpg&fm=jpg", "Luxury perfume"));
        productRepository.save(new Product("Foundation", "beauty", new BigDecimal("800"), "30ml", "https://media6.ppl-media.com//tr:h-235,w-235,c-at_max,dpr-2,q-40/static/img/product/344732/ny-bae-3-in-1-primer-foundation-serum-warm-cashew-03-30-ml-82_1_display_1754664234_9f6773f8.jpg", "Liquid foundation"));
        productRepository.save(new Product("Mascara", "beauty", new BigDecimal("400"), "1 piece", "https://www.lakmeindia.com/cdn/shop/files/29112_H-8901030859073_800x.jpg?v=1742202692", "Waterproof mascara"));
        productRepository.save(new Product("Face Wash", "beauty", new BigDecimal("200"), "150ml", "https://www.pinkroot.in/cdn/shop/files/orange-face-wash-for-tan-removalor-pimple-control-100ml-pink-root-1.png?v=1725009761", "Gentle face wash"));
        productRepository.save(new Product("Moisturizer", "beauty", new BigDecimal("350"), "100ml", "https://plumgoodness.com/cdn/shop/files/nia-gel-moodshot-website.jpg?v=1760599846&width=1214", "Daily moisturizer"));

        // Men's Clothing
        productRepository.save(new Product("Cotton T-Shirt", "mens-clothing", new BigDecimal("299"), "1 piece", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300&h=200&fit=crop", "Comfortable cotton t-shirt"));
        productRepository.save(new Product("Formal Shirt", "mens-clothing", new BigDecimal("799"), "1 piece", "https://images.unsplash.com/photo-1596362051514-4969f3688911?w=300&h=200&fit=crop", "Professional formal shirt"));
        productRepository.save(new Product("Denim Jeans", "mens-clothing", new BigDecimal("1299"), "1 piece", "https://images.unsplash.com/photo-1542272604-787c62d465d1?w=300&h=200&fit=crop", "Classic blue denim jeans"));
        productRepository.save(new Product("Casual Pants", "mens-clothing", new BigDecimal("899"), "1 piece", "https://images.unsplash.com/photo-1473080169858-d828d75f7979?w=300&h=200&fit=crop", "Comfortable casual pants"));
        productRepository.save(new Product("Polo Shirt", "mens-clothing", new BigDecimal("599"), "1 piece", "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=300&h=200&fit=crop", "Classic polo shirt"));
        productRepository.save(new Product("Jacket", "mens-clothing", new BigDecimal("2499"), "1 piece", "https://images.unsplash.com/photo-1551028719-00167b16ebc5?w=300&h=200&fit=crop", "Stylish winter jacket"));
        productRepository.save(new Product("Shorts", "mens-clothing", new BigDecimal("499"), "1 piece", "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=300&h=200&fit=crop", "Comfortable shorts"));
        productRepository.save(new Product("Sweater", "mens-clothing", new BigDecimal("1199"), "1 piece", "https://images.unsplash.com/photo-1556821552-5f63b1c2c723?w=300&h=200&fit=crop", "Cozy wool sweater"));

        // Women's Clothing
        productRepository.save(new Product("Women T-Shirt", "womens-clothing", new BigDecimal("349"), "1 piece", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300&h=200&fit=crop", "Stylish women t-shirt"));
        productRepository.save(new Product("Women Jeans", "womens-clothing", new BigDecimal("1399"), "1 piece", "https://images.unsplash.com/photo-1542272604-787c62d465d1?w=300&h=200&fit=crop", "Trendy women jeans"));
        productRepository.save(new Product("Saree", "womens-clothing", new BigDecimal("1999"), "1 piece", "https://images.unsplash.com/photo-1609122416994-94deaf6c3e3f?w=300&h=200&fit=crop", "Traditional saree"));
        productRepository.save(new Product("Kurti", "womens-clothing", new BigDecimal("799"), "1 piece", "https://images.unsplash.com/photo-1609122416994-94deaf6c3e3f?w=300&h=200&fit=crop", "Ethnic kurti"));
        productRepository.save(new Product("Dress", "womens-clothing", new BigDecimal("1299"), "1 piece", "https://images.unsplash.com/photo-1595777707802-221b42c0bbb2?w=300&h=200&fit=crop", "Elegant dress"));
        productRepository.save(new Product("Leggings", "womens-clothing", new BigDecimal("499"), "1 piece", "https://images.unsplash.com/photo-1506529082632-401017062e57?w=300&h=200&fit=crop", "Comfortable leggings"));
        productRepository.save(new Product("Skirt", "womens-clothing", new BigDecimal("899"), "1 piece", "https://images.unsplash.com/photo-1612336307429-8a88e8d08dbb?w=300&h=200&fit=crop", "Stylish skirt"));
        productRepository.save(new Product("Blazer", "womens-clothing", new BigDecimal("2199"), "1 piece", "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=300&h=200&fit=crop", "Professional blazer"));

        // Kids Clothing
        productRepository.save(new Product("Kids T-Shirt", "kids-clothing", new BigDecimal("249"), "1 piece", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300&h=200&fit=crop", "Colorful kids t-shirt"));
        productRepository.save(new Product("Kids Jeans", "kids-clothing", new BigDecimal("699"), "1 piece", "https://images.unsplash.com/photo-1542272604-787c62d465d1?w=300&h=200&fit=crop", "Durable kids jeans"));
        productRepository.save(new Product("Kids Dress", "kids-clothing", new BigDecimal("599"), "1 piece", "https://images.unsplash.com/photo-1595777707802-221b42c0bbb2?w=300&h=200&fit=crop", "Pretty kids dress"));
        productRepository.save(new Product("Kids Shorts", "kids-clothing", new BigDecimal("399"), "1 piece", "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=300&h=200&fit=crop", "Comfortable kids shorts"));
        productRepository.save(new Product("Kids Jacket", "kids-clothing", new BigDecimal("1299"), "1 piece", "https://images.unsplash.com/photo-1551028719-00167b16ebc5?w=300&h=200&fit=crop", "Warm kids jacket"));
        productRepository.save(new Product("Kids Sweater", "kids-clothing", new BigDecimal("699"), "1 piece", "https://images.unsplash.com/photo-1556821552-5f63b1c2c723?w=300&h=200&fit=crop", "Cozy kids sweater"));
        productRepository.save(new Product("Kids Shoes", "kids-clothing", new BigDecimal("799"), "1 pair", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&h=200&fit=crop", "Comfortable kids shoes"));
        productRepository.save(new Product("Kids Socks", "kids-clothing", new BigDecimal("199"), "3 pairs", "https://images.unsplash.com/photo-1556821552-5f63b1c2c723?w=300&h=200&fit=crop", "Colorful kids socks"));
    }

    private void initializeCoupons() {
        // Create sample coupons for testing
        Coupon coupon1 = new Coupon();
        coupon1.setCode("SAVE10");
        coupon1.setDiscountPercentage(10);
        coupon1.setMinOrderAmount(new BigDecimal("100"));
        coupon1.setMaxUses(100);
        coupon1.setUsedCount(0);
        coupon1.setActive(true);
        couponRepository.save(coupon1);

        Coupon coupon2 = new Coupon();
        coupon2.setCode("WELCOME20");
        coupon2.setDiscountPercentage(20);
        coupon2.setMinOrderAmount(new BigDecimal("200"));
        coupon2.setMaxUses(50);
        coupon2.setUsedCount(0);
        coupon2.setActive(true);
        couponRepository.save(coupon2);

        Coupon coupon3 = new Coupon();
        coupon3.setCode("BIGDEAL");
        coupon3.setDiscountPercentage(15);
        coupon3.setMinOrderAmount(new BigDecimal("500"));
        coupon3.setMaxUses(25);
        coupon3.setUsedCount(0);
        coupon3.setActive(true);
        couponRepository.save(coupon3);
    }
}
