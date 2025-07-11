package org.kinetic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kinetic.entity.converter.FileNamesConverter;
import org.kinetic.entity.converter.WordFreqConverter;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "text_stats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextStatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private Integer wordCount;
    private Integer lineCount;

    @Column(columnDefinition = "text")
    @Convert(converter = FileNamesConverter.class)
    private List<String> fileNames;

    @Column(columnDefinition = "text")
    @Convert(converter = WordFreqConverter.class)
    private Map<String, Integer> wordsFreq;
}