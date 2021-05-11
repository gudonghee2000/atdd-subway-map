package wooteco.subway.domain.line.section;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import wooteco.util.SectionFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SectionsTest {

    private Sections sections;

    @BeforeEach
    void setUp() {
        sections = new Sections(
                Arrays.asList(
                        new Section(1L, 1L, 3L, 4L, 10L)
                )
        );
    }

    @DisplayName("상행 종점 등록")
    @Test
    void add_addUpStation() {
        sections.add(SectionFactory.create(2L, 1L, 2L, 3L, 10L));

        assertThat(sections.getStationIds()).containsExactly(2L, 3L, 4L);

        assertThat(findDistanceByUpAndDownStationId(2L, 3L)).isEqualTo(10L);
        assertThat(findDistanceByUpAndDownStationId(3L, 4L)).isEqualTo(10L);
    }

    @DisplayName("하행 종점 등록")
    @Test
    void add_addDownStation() {
        sections.add(SectionFactory.create(2L, 1L, 4L, 5L, 10L));

        assertThat(sections.getStationIds()).containsExactly(3L, 4L, 5L);
        assertThat(findDistanceByUpAndDownStationId(3L, 4L)).isEqualTo(10L);
        assertThat(findDistanceByUpAndDownStationId(4L, 5L)).isEqualTo(10L);
    }

    @DisplayName("갈래길 방지(상행)")
    @Test
    void add_preventForkedRoadByUpStation() {
        sections.add(SectionFactory.create(2L, 1L, 3L, 5L, 4L));

        assertThat(sections.getStationIds()).containsExactly(3L, 5L, 4L);
        assertThat(findDistanceByUpAndDownStationId(3L, 5L)).isEqualTo(4L);
        assertThat(findDistanceByUpAndDownStationId(5L, 4L)).isEqualTo(6L);
    }

    @DisplayName("갈래길 방지(하행)")
    @Test
    void add_preventForkedRoadByDownStation() {
        sections.add(SectionFactory.create(2L, 1L, 5L, 4L, 4L));

        assertThat(sections.getStationIds()).containsExactly(3L, 5L, 4L);
        assertThat(findDistanceByUpAndDownStationId(3L, 5L)).isEqualTo(6L);
        assertThat(findDistanceByUpAndDownStationId(5L, 4L)).isEqualTo(4L);
    }

    @DisplayName("섹션 등록 시 기존 역 사이 길이보다 크거나 같으면 예외")
    @ParameterizedTest
    @CsvSource(value = {"5:4:10", "5:4:11", "3:5:10", "3:5:11"}, delimiter = ':')
    void add_exceptionWhenIsGreaterThanOrEqualToTheDistanceBetweenExistingStationsWhenRegisteringASection(
            Long upStationId,
            Long downStationId,
            Long distance
    ) {
        assertThatThrownBy(() ->
                sections.add(
                        SectionFactory.create(2L, 1L, upStationId, downStationId, distance)
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없습니다");
    }

    @DisplayName("상행역과 하행역이 이미 노선에 모두 등록되어 있다면 예외")
    @ParameterizedTest
    @CsvSource(value = {"3:4", "4:3"}, delimiter = ':')
    void add_exceptionIfBothTheUpAndDownStationIdsAreAlreadyRegistered(
            Long upStationId,
            Long downStationId
    ) {
        assertThatThrownBy(() ->
                sections.add(
                        SectionFactory.create(2L, 1L, upStationId, downStationId, 1L)
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상행선과 하행선이 이미 존재합니다");
    }

    @DisplayName("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 예외")
    @Test
    void add_exceptionIfBothTheUpAndDownStationsAreNotContainedInLine() {
        assertThatThrownBy(() ->
                sections.add(
                        SectionFactory.create(2L, 1L, 5L, 6L, 1L)
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("연결할 수 있는 역을 찾을 수 없습니다.");
    }

    @Test
    void getStationIds() {
        //init 3-4

        sections.add(SectionFactory.create(2L, 1L, 3L, 5L, 5L));
        //3-5-4
        sections.add(SectionFactory.create(2L, 1L, 4L, 6L, 1L));
        //3-5-4-6
        sections.add(SectionFactory.create(2L, 1L, 1L, 3L, 1L));
        //1-3-5-4-6

        assertThat(sections.getStationIds()).containsExactly(1L, 3L, 5L, 4L, 6L);
    }

    private Long findDistanceByUpAndDownStationId(Long upStationId, Long downStationId) {
        return sections.getSections().stream()
                .filter(section -> Objects.equals(section.getUpStationId(), upStationId))
                .filter(section -> Objects.equals(section.getDownStationId(), downStationId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("matching failed"))
                .getDistance();
    }

}