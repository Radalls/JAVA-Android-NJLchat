package fr.leojadenadir.projetandroid.model.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

import javax.persistence.*;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    String id;

    @ManyToOne
    User author;
    
    LocalDateTime createdDate;
    String content;
}
