package catalog.core.repository;

import catalog.core.model.Actor;

import java.util.List;

public interface ActorRepository extends CatalogRepository<Actor, Long>{
    List<Actor> findByOrderByPopularityAsc();
    List<Actor> findAllByOrderByAgeAsc();
}

