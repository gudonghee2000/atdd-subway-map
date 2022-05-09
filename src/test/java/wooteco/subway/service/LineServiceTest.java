package wooteco.subway.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.dao.FakeLineDao;
import wooteco.subway.dao.FakeSectionDao;
import wooteco.subway.dao.FakeStationDao;
import wooteco.subway.dto.LineRequest;
import wooteco.subway.dto.LineResponse;
import wooteco.subway.exception.DataNotFoundException;
import wooteco.subway.exception.DuplicateNameException;

class LineServiceTest {

    private LineService lineService;

    @BeforeEach
    void setUp() {
        lineService = new LineService(new FakeLineDao(), new FakeStationDao(), new FakeSectionDao());
        FakeLineDao.init();
        FakeSectionDao.init();
        FakeStationDao.init();
    }

    @DisplayName("노선, 구간 정보를 저장한다.")
    @Test
    void save() {
        LineRequest line = new LineRequest("4호선", "green", 1L, 2L, 10);
        LineResponse newLine = lineService.createLine(line);

        assertThat(line.getName()).isEqualTo(newLine.getName());
        assertThat(line.getColor()).isEqualTo(newLine.getColor());

        assertThat(line.getUpStationId()).isEqualTo(newLine.getStations().get(0).getId());
        assertThat(line.getDownStationId()).isEqualTo(newLine.getStations().get(1).getId());
    }

    @DisplayName("노선, 구간 정보를 저장할때, 노선과 구간 정보에 존재하지 않는 지하철역 정보가 있으면 예외를 발생한다.")
    @Test
    void save_no_data_exception() {
        LineRequest line = new LineRequest("4호선", "green", 1L, 7L, 10);

        assertThatThrownBy(() -> lineService.createLine(line))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("존재하지 않는 역입니다.");
    }

    @DisplayName("중복된 노선 저장시 예외")
    @Test
    void duplicateLine() {
        LineRequest line = new LineRequest("3호선", "green", 1L, 2L, 10);
        LineRequest duplicateLine = new LineRequest("3호선", "red", 1L, 2L, 10);
        lineService.createLine(line);

        assertThatThrownBy(() -> lineService.createLine(duplicateLine))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessageContaining("이미 등록된 지하철 노선이름 입니다.");
    }

/*
    @DisplayName("노선 정보 전체 조회")
    @Test
    void findAll() {
        lineService.createLine(new LineRequest("5호선", "green"));
        lineService.createLine(new LineRequest("7호선", "red"));

        List<LineResponse> lines = lineService.findLines();
        lines.stream()
                .filter(line -> line.getName().equals("5호선"))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("노선 정보가 없습니다."));
    }

    @Test
    @DisplayName("노선 정보 삭제")
    void delete() {
        LineRequest line = new LineRequest("4호선", "red");
        LineResponse newLine = lineService.createLine(line);

        assertThat(lineService.deleteLine(newLine.getId())).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 노선정보 삭제시 예외를 발생한다.")
    void deleteNotExistLine() {
        assertThatThrownBy(() -> lineService.deleteLine(12L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("존재하지 않는 노선입니다.");
    }

    @Test
    @DisplayName("노선 정보 업데이트")
    void update() {
        LineRequest line = new LineRequest("11호선", "red");
        LineRequest lineForUpdate = new LineRequest("신분당선", "red");

        LineResponse newLine = lineService.createLine(line);

        assertThat(lineService.updateLine(newLine.getId(), lineForUpdate)).isEqualTo(1);
    }

    @Test
    @DisplayName("중복되는 이름으로 노선이름 업데이트시 예외를 발생한다.")
    void updateNotExistLine() {
        LineRequest line = new LineRequest("11호선", "red");
        LineResponse newLine = lineService.createLine(line);

        assertThatThrownBy(() -> lineService.updateLine(newLine.getId(), line))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessageContaining("이미 등록된 지하철 노선이름 입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 노선정보 업데이트시 예외를 발생한다.")
    void updateDuplicateNameLine() {
        LineRequest line = new LineRequest("11호선", "red");
        LineRequest lineForUpdate = new LineRequest("11호선", "red");

        LineResponse newLine = lineService.createLine(line);

        assertThatThrownBy(() -> lineService.updateLine(12L, lineForUpdate))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("존재하지 않는 노선입니다.");
    }

    @Test
    @DisplayName("노선 정보 조회")
    void find() {
        LineRequest line = new LineRequest("4호선", "red");
        LineResponse newLine = lineService.createLine(line);

        assertThat(lineService.findLine(newLine.getId()).getName()).isEqualTo(line.getName());
        assertThat(lineService.findLine(newLine.getId()).getColor()).isEqualTo(line.getColor());
    }

    @Test
    @DisplayName("존재하지 않는 노선정보 조회시 예외를 발생한다.")
    void findNotExistLine() {
        assertThatThrownBy(() -> lineService.findLine(12L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("존재하지 않는 노선입니다.");
    }

 */
}