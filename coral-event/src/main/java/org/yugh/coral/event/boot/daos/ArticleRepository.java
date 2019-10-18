package org.yugh.coral.event.boot.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yugh.coral.event.boot.domain.Article;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findAllByPublicationDate(Date publicationDate);

    List<Article> findAllByPublicationTimeBetween(Date publicationTimeStart,
                                                  Date publicationTimeEnd);

    @Query("select a from Article a where a.creationDateTime <= :creationDateTime")
    List<Article> findAllWithCreationDateTimeBefore(
            @Param("creationDateTime") Date creationDateTime);

}
