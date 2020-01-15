package whu.graph.pay.domain;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public abstract class Entity {
	protected Long id;

	 public Long getId() {
		 return id;
	 }
}
