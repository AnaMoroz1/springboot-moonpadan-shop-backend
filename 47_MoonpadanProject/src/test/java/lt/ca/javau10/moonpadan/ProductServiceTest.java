//package lt.ca.javau10.moonpadan.services;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import lt.ca.javau10.moonpadan.entities.Product;
//import lt.ca.javau10.moonpadan.repositories.ProductRepository;
//
//public class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); // Inicijuoja Mockito objektus
//    }
//
//    @Test
//    void testGetAllProducts() {
//        // Arrange
//        Product product1 = new Product(1L, "Product1", "Category1", "Description1", 100.0, null);
//        Product product2 = new Product(2L, "Product2", "Category2", "Description2", 200.0, null);
//        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
//
//        // Act
//        List<Product> products = productService.getAllProducts();
//
//        // Assert
//        assertEquals(2, products.size());
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetProductById() {
//        // Arrange
//        Product product = new Product(1L, "Product1", "Category1", "Description1", 100.0, null);
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        // Act
//        Optional<Product> result = productService.getProductById(1L);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(product.getId(), result.get().getId());
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testGetProductByName() {
//        // Arrange
//        Product product = new Product(1L, "Product1", "Category1", "Description1", 100.0, null);
//        when(productRepository.findByName("Product1")).thenReturn(Optional.of(product));
//
//        // Act
//        Optional<Product> result = productService.getProductByName("Product1");
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(product.getName(), result.get().getName());
//        verify(productRepository, times(1)).findByName("Product1");
//    }
//
//    @Test
//    void testAddProduct() {
//        // Arrange
//        Product product = new Product(1L, "Product1", "Category1", "Description1", 100.0, null);
//        when(productRepository.save(product)).thenReturn(product);
//
//        // Act
//        Product savedProduct = productService.addProduct(product);
//
//        // Assert
//        assertNotNull(savedProduct);
//        assertEquals(product.getName(), savedProduct.getName());
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    void testUpdateProduct() {
//        // Arrange
//        Product existingProduct = new Product(1L, "Product1", "Category1", "Description1", 100.0, null);
//        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedCategory", "UpdatedDescription", 150.0, null);
//        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
//        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
//
//        // Act
//        Product result = productService.updateProduct(1L, updatedProduct);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("UpdatedProduct", result.getName());
//        verify(productRepository, times(1)).findById(1
