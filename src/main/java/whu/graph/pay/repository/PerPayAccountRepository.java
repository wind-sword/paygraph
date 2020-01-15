package whu.graph.pay.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import whu.graph.pay.domain.Node;



@Repository
public interface PerPayAccountRepository extends CrudRepository<Node, Long>{
	
	@Query("MATCH (n:个人支付账户) WHERE n.Acc_name = {accname} RETURN n UNION MATCH (n:单位支付账户) WHERE n.Acc_name = {accname} RETURN n") 
	Collection<Node> findByAccName(@Param("accname") String accname);
	
	@Query("MATCH (n:个人支付账户) WHERE n.Acc_no = {accno} RETURN n UNION MATCH (n:单位支付账户) WHERE n.Acc_no = {accno} RETURN n UNION MATCH (n:支付账户) WHERE n.Acc_no = {accno} RETURN n") 
	Collection<Node> findByAccNo(@Param("accno") String accno);
	
	@Query("MATCH (n:其他商户) WHERE n.Acc_name = {cname} RETURN n UNION MATCH (n:对公商户) WHERE n.Acc_name = {cname} RETURN n UNION MATCH (n:个体商户) WHERE n.Acc_name = {cname} RETURN n UNION MATCH (n:小微商户) WHERE n.Acc_name = {cname} RETURN n") 
	Collection<Node> findByComName(@Param("cname") String cname);
	
	@Query("MATCH (n:其他商户) WHERE n.Org_codeJoin_code1 = {cno} RETURN n UNION MATCH (n:个体商户) WHERE n.Org_codeJoin_code1 = {cno} RETURN n UNION MATCH (n:对公商户) WHERE n.Org_codeJoin_code1 = {cno} RETURN n UNION MATCH (n:小微商户) WHERE n.Org_codeJoin_code1 = {cno} RETURN n") 
	Collection<Node> findByComNo(@Param("cno") String cno);
	
}
