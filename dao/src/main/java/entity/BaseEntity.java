package entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -2883330545357288318L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
