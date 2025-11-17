package hello.delivery.store.domain;

import static java.util.Objects.requireNonNullElseGet;

import hello.delivery.common.exception.StoreException;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import hello.delivery.store.infrastructure.StoreType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Store {

    private final Long id;
    private final Owner owner;
    private final String name;
    private final StoreType storeType;
    private final List<Product> products;
    private final int dailySales;
    private final int totalSales;
    private final LocalDate openDate;
    private final LocalDate lastSalesDate;

    @Builder
    private Store(Long id, Owner owner, String name, int dailySales, int totalSales, StoreType storeType,
                  List<Product> products, LocalDate openDate,
                  LocalDate lastSalesDate) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.dailySales = dailySales;
        this.totalSales = totalSales;
        this.storeType = storeType;
        this.products = requireNonNullElseGet(products, ArrayList::new);
        this.openDate = openDate;
        this.lastSalesDate = lastSalesDate;
    }

    public static Store of(StoreCreate storeCreate, Owner owner, LocalDate currentDate) {
        validate(storeCreate, owner);
        return Store.builder()
                .owner(owner)
                .name(storeCreate.getStoreName())
                .storeType(storeCreate.getStoreType())
                .dailySales(0)
                .totalSales(0)
                .products(new ArrayList<>())
                .openDate(currentDate)
                .lastSalesDate(null)
                .build();
    }

    public Store addTotalSales(int amount, LocalDate currentDate) {
        int newTotalSales = totalSales + amount;
        int newDailySales = amount;
        LocalDate newLastSalesDate = currentDate;

        if (currentDate.equals(lastSalesDate)) {
            newDailySales = dailySales + amount;
            newLastSalesDate = lastSalesDate;
        }

        return Store.builder()
                .owner(owner)
                .name(name)
                .storeType(storeType)
                .dailySales(newDailySales)
                .totalSales(newTotalSales)
                .products(products)
                .openDate(openDate)
                .lastSalesDate(newLastSalesDate)
                .build();
    }

    private static void validate(StoreCreate storeCreate, Owner owner) {
        if (owner == null) {
            throw new StoreException("가게 주인은 필수입니다.");
        }
        if (storeCreate.getStoreName() == null || storeCreate.getStoreName().isBlank()) {
            throw new StoreException("가게 이름은 필수 입력 값입니다.");
        }
        if (storeCreate.getStoreType() == null) {
            throw new StoreException("가게 타입은 필수 입력 값입니다.");
        }
    }

    public boolean isNotOwner(Owner owner) {
        return !this.owner.getId().equals(owner.getId());
    }

}
