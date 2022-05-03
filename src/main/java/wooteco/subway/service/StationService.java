package wooteco.subway.service;

import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Station;
import wooteco.subway.dto.StationRequest;
import wooteco.subway.dto.StationResponse;
import wooteco.subway.exception.ClientException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StationService {

    public static StationResponse createStation(StationRequest stationRequest) {
        validateDuplicateStation(stationRequest);
        Station station = new Station(stationRequest.getName());
        Station newStation = StationDao.save(station);
        return new StationResponse(newStation.getId(), newStation.getName());
    }

    private static void validateDuplicateStation(StationRequest stationRequest) {
        Optional<Station> optional = StationDao.findAll()
                .stream()
                .filter(station -> station.getName().equals(stationRequest.getName()))
                .findAny();
        if (optional.isPresent()) {
            throw new ClientException("이미 등록된 지하철역입니다.");
        }
    }

    public static List<StationResponse> findAll() {
        List<Station> stations = StationDao.findAll();
        return stations.stream()
                .map(it -> new StationResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }

    public static void deleteStation(long id) {
        StationDao.deleteStation(id);
    }
}
