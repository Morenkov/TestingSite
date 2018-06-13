package ru.ibs.testing.site.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @PositiveOrZero(message = "Please zero or more")
    private int points;

    @NotBlank(message = "Please fill the answer")
    @Length(max = 100, message = "answer too long (more than 100)")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
