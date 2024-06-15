package org.quarkus.business.service.impl;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.openapitools.client.model.ProductRequestDTO;
import org.quarkus.business.document.Product;
import org.quarkus.business.respository.ProductRepository;
import org.quarkus.business.service.ProductService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

@Singleton
public class ProductImpl implements ProductService {

    @Inject
    ProductRepository productRepository;

    @Override
    public Uni<Product> saveProduct(Product product) {
        return productRepository.persist(product);
    }

    @Override
    public Uni<List<Product>> listProducts() {
        throw new IllegalArgumentException("El nombre del influencer no puede ser nulo o vacío");
        //return Uni.createFrom().failure(new IllegalArgumentException("El nombre del influencer no puede ser nulo o vacío"));
        //return productRepository.listAll();
    }

    @Override
    public Uni<Product> getProduct(ObjectId id) {
        return productRepository.findById(id);
    }

    @Override
    public Uni<List<Product>> getProductBy(String userId, String categoryId) {
        String query = String.format("{ userId: {$regex: '^%s$'}, categoryId: {$regex: '^%s$'} }", userId, categoryId);
        return productRepository.find(query).list();
    }

    @Override
    public Uni<Product> deleteProduct(ObjectId id) {
        return productRepository.findById(id)
                .onItem().transformToUni(product -> productRepository.delete(product).onItem().transform(x -> product));
    }


    /*@Override
    public Multi<Product> findAllByProviders() {
        return Multi.createFrom().empty(); // Implement this method based on your logic
    }

    @Override
    public Multi<Product> findAll() {
        return productRepository.streamAll();
    }

    @Override
    public Uni<Product> insert(ProductRequestDTO productRequestDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Product product = new Product();
        product.name = productRequestDTO.getName();
        product.shortDescription = productRequestDTO.getShortDescription();
        product.description = productRequestDTO.getDescription();
        //product.reviewRatings = productRequestDTO.getReviewRatings();
       // product.relatedProducts = new ArrayList<>(); // Or set based on productRequestDTO.getRelatedProducts()
        //product.crossSellProducts = new ArrayList<>(); // Or set based on productRequestDTO.getCrossSellProducts()
        /*product.productThumbnail = mapToImage(productRequestDTO.getProductThumbnail());
        product.productGalleries = mapToImageList(productRequestDTO.getProductGalleries());
        product.productMetaImage = mapToImage(productRequestDTO.getProductMetaImage());
        product.sizeChartImage = mapToImage(productRequestDTO.getSizeChartImage());
        product.reviews = mapToReviewList(productRequestDTO.getReviews());*/
        //product.createdAt = timestamp.toString();
       // product.updatedAt = timestamp.toString();

        /*return productRepository.persist(product).replaceWith(product);
    }*/



    /*private Product.Image mapToImage(ProductRequestDTO.ImageDTO imageDTO) {
        if (imageDTO == null) {
            return null;
        }
        Product.Image image = new Product.Image();
        image.id = imageDTO.getId();
        image.collectionName = imageDTO.getCollectionName();
        image.name = imageDTO.getName();
        image.fileName = imageDTO.getFileName();
        image.mimeType = imageDTO.getMimeType();
        image.disk = imageDTO.getDisk();
        image.conversionsDisk = imageDTO.getConversionsDisk();
        image.size = imageDTO.getSize();
        image.createdById = imageDTO.getCreatedById();
        image.createdAt = imageDTO.getCreatedAt();
        image.updatedAt = imageDTO.getUpdatedAt();
        image.originalUrl = imageDTO.getOriginalUrl();
        return image;
    }

    private List<Product.Image> mapToImageList(List<ProductRequestDTO.ImageDTO> imageDTOList) {
        if (imageDTOList == null) {
            return new ArrayList<>();
        }
        List<Product.Image> images = new ArrayList<>();
        for (ProductRequestDTO.ImageDTO imageDTO : imageDTOList) {
            images.add(mapToImage(imageDTO));
        }
        return images;
    }

    private List<Product.Review> mapToReviewList(List<ProductRequestDTO.ReviewDTO> reviewDTOList) {
        if (reviewDTOList == null) {
            return new ArrayList<>();
        }
        List<Product.Review> reviews = new ArrayList<>();
        for (ProductRequestDTO.ReviewDTO reviewDTO : reviewDTOList) {
            Product.Review review = new Product.Review();
            review.id = reviewDTO.getId();
            review.productId = reviewDTO.getProductId();
            review.consumerId = reviewDTO.getConsumerId();
            review.storeId = reviewDTO.getStoreId();
            review.reviewImageId = reviewDTO.getReviewImageId();
            review.rating = reviewDTO.getRating();
            review.description = reviewDTO.getDescription();
            review.createdAt = reviewDTO.getCreatedAt();
            review.updatedAt = reviewDTO.getUpdatedAt();
            review.deletedAt = reviewDTO.getDeletedAt();
            review.reviewImage = mapToImage(reviewDTO.getReviewImage());
            review.consumer = mapToConsumer(reviewDTO.getConsumer());
            reviews.add(review);
        }
        return reviews;
    }

    private Product.Consumer mapToConsumer(ProductRequestDTO.ConsumerDTO consumerDTO) {
        if (consumerDTO == null) {
            return null;
        }
        Product.Consumer consumer = new Product.Consumer();
        consumer.id = consumerDTO.getId();
        consumer.name = consumerDTO.getName();
        consumer.email = consumerDTO.getEmail();
        consumer.countryCode = consumerDTO.getCountryCode();
        consumer.phone = consumerDTO.getPhone();
        consumer.profileImageId = consumerDTO.getProfileImageId();
        consumer.systemReserve = consumerDTO.getSystemReserve();
        consumer.status = consumerDTO.getStatus();
        consumer.createdById = consumerDTO.getCreatedById();
        consumer.emailVerifiedAt = consumerDTO.getEmailVerifiedAt();
        consumer.createdAt = consumerDTO.getCreatedAt();
        consumer.updatedAt = consumerDTO.getUpdatedAt();
        consumer.deletedAt = consumerDTO.getDeletedAt();
        consumer.ordersCount = consumerDTO.getOrdersCount();
        consumer.role = mapToRole(consumerDTO.getRole());
        consumer.point = mapToPoint(consumerDTO.getPoint());
        consumer.wallet = mapToWallet(consumerDTO.getWallet());
        consumer.address = mapToAddressList(consumerDTO.getAddress());
        consumer.vendorWallet = mapToVendorWallet(consumerDTO.getVendorWallet());
        return consumer;
    }

    private Product.Role mapToRole(ProductRequestDTO.RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        Product.Role role = new Product.Role();
        role.id = roleDTO.getId();
        role.name = roleDTO.getName();
        role.guardName = roleDTO.getGuardName();
        role.systemReserve = roleDTO.getSystemReserve();
        role.createdAt = roleDTO.getCreatedAt();
        role.updatedAt = roleDTO.getUpdatedAt();
        role.pivot = mapToPivot(roleDTO.getPivot());
        return role;
    }

    private Product.Pivot mapToPivot(ProductRequestDTO.PivotDTO pivotDTO) {
        if (pivotDTO == null) {
            return null;
        }
        Product.Pivot pivot = new Product.Pivot();
        pivot.modelId = pivotDTO.getModelId();
        pivot.roleId = pivotDTO.getRoleId();
        pivot.modelType = pivotDTO.getModelType();
        return pivot;
    }

    private Product.Point mapToPoint(ProductRequestDTO.PointDTO pointDTO) {
        if (pointDTO == null) {
            return null;
        }
        Product.Point point = new Product.Point();
        point.id = pointDTO.getId();
        point.consumerId = pointDTO.getConsumerId();
        point.balance = pointDTO.getBalance();
        return point;
    }

    private Product.Wallet mapToWallet(ProductRequestDTO.WalletDTO walletDTO) {
        if (walletDTO == null) {
            return null;
        }
        Product.Wallet wallet = new Product.Wallet();
        wallet.id = walletDTO.getId();
        wallet.consumerId = walletDTO.getConsumerId();
        wallet.balance = walletDTO.getBalance();
        return wallet;
    }

    private List<Product.Address> mapToAddressList(List<ProductRequestDTO.AddressDTO> addressDTOList) {
        if (addressDTOList == null) {
            return new ArrayList<>();
        }
        List<Product.Address> addresses = new ArrayList<>();
        for (ProductRequestDTO.AddressDTO addressDTO : addressDTOList) {
            Product.Address address = new Product.Address();
            address.id = addressDTO.getId();
            address.title = addressDTO.getTitle();
            address.userId = addressDTO.getUserId();
            address.street = addressDTO.getStreet();
            address.city = addressDTO.getCity();
            address.pincode = addressDTO.getPincode();
            address.isDefault = addressDTO.getIsDefault();
            address.countryCode = addressDTO.getCountryCode();
            address.phone = addressDTO.getPhone();
            address.country = mapToCountry(addressDTO.getCountry());
            address.state = mapToState(addressDTO.getState());
            addresses.add(address);
        }
        return addresses;
    }

    private Product.Country mapToCountry(ProductRequestDTO.CountryDTO countryDTO) {
        if (countryDTO == null) {
            return null;
        }
        Product.Country country = new Product.Country();
        country.id = countryDTO.getId();
        country.name = countryDTO.getName();
        return country;
    }

    private Product.State mapToState(ProductRequestDTO.StateDTO stateDTO) {
        if (stateDTO == null) {
            return null;
        }
        Product.State state = new Product.State();
        state.id = stateDTO.getId();
        state.name = stateDTO.getName();
        state.countryId = stateDTO.getCountryId();
        state.createdAt = stateDTO.getCreatedAt();
        state.updatedAt = stateDTO.getUpdatedAt();
        return state;
    }

    private Product.VendorWallet mapToVendorWallet(ProductRequestDTO.VendorWalletDTO vendorWalletDTO) {
        if (vendorWalletDTO == null) {
            return null;
        }
        // Map the fields accordingly
        return new Product.VendorWallet();
    }*/


    /*@Override
    public Uni<Product> findById(String id) {
        return null;
    }

    @Override
    public Uni<Product> update(String id, ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public Uni<Boolean> delete(String id) {
        return null;
    }*/
}
