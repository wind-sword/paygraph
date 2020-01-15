package whu.graph.pay.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import whu.graph.pay.domain.Relationship;

@Repository
public interface RelationshipRepository  extends PagingAndSortingRepository<Relationship, Long>{
	
	@Query("MATCH s=(n)-[r]-(p) WHERE id(n)={id} RETURN s LIMIT 100 UNION MATCH s=(n)-[r:总交易]-(p) where id(n)={id} return s limit 100 UNION MATCH s=(n)-[r:使用]-(p) where id(n)={id} return s limit 100")//通过id扩展节点
	Collection<Relationship> extendById(@Param("id") Long id);
	
//	@Query("MATCH s=(n)-[r]-(p) WHERE id(n)={id} RETURN s")//通过id扩展节点1
//	Collection<Use> extendById1(@Param("id") Long id);
	
}
