package org.example.jpa.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Student")
public class Student {

    @Id
    private UUID id;

    @Column( nullable = false)
    private String name;

    @Column( unique = true ,nullable = false)
    private String email;

    @Column( nullable = false)
    private int age;

    @Column
    private float marks;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "id_address")
    private UUID idAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false )
    private Address address;



}
