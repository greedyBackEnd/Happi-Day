package com.happiday.Happi_Day.domain.entity.product;

import com.happiday.Happi_Day.domain.entity.BaseEntity;
import com.happiday.Happi_Day.domain.entity.article.Hashtag;
import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "sales")
@SQLDelete(sql = "UPDATE sales SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Sales extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매글 카테고리 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SalesCategory salesCategory;

    // 판매자 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User users;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    @Setter
    private String thumbnailImage;

    @Setter
    @ElementCollection
    private List<String> imageUrl = new ArrayList<>();

    @Column(nullable = false)
    private String account;

    @Column
    private String ectArtists;

    @Column
    private String ectTeams;

    // 상품
    @OneToMany(mappedBy = "sales")
    private List<Product> products = new ArrayList<>();

    // 주문 매핑
    @OneToMany(mappedBy = "sales")
    private List<Order> orders = new ArrayList<>();

    // 판매글 찜하기
    @ManyToMany(mappedBy = "salesLikes")
    private List<User> salesLikesUsers = new ArrayList<>();

    // 아티스트-판매글 매핑
    @ManyToMany(mappedBy = "salesList")
    private List<Artist> artists = new ArrayList<>();

    // 팀-판매글 매핑
    @ManyToMany(mappedBy = "salesList")
    private List<Team> teams = new ArrayList<>();

    // 해시태그 매핑
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "sales_hashtag",
            joinColumns = @JoinColumn(name = "sales_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags = new ArrayList<>();

    public void updateProduct(List<Product> products) {
        this.products = products;
    }

    public void updateSales(Sales sales) {
        if (sales.getName() != null) this.name = sales.getName();
        if (sales.getDescription() != null) this.description = sales.getDescription();
//        if (sales.getProducts() != null) this.products = sales.getProducts();
        if (sales.getSalesStatus() != null) this.salesStatus = sales.getSalesStatus();
        if (sales.getArtists() != null) this.artists = sales.getArtists();
        if (sales.getTeams() != null) this.teams = sales.getTeams();
        if (sales.getHashtags() != null) this.hashtags = sales.getHashtags();
        if (sales.getEctArtists() != null) this.ectArtists = sales.getEctArtists();
        if (sales.getEctTeams() != null) this.ectTeams = sales.getEctTeams();

    }
}
