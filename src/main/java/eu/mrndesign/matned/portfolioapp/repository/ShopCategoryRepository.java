package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Long> {

    @Query("select sc from ShopCategory sc where sc.categoryName = ?1")
    Optional<ShopCategory> findByCategoryName(String categoryName);

}
