const BASE_URL = "localhost:8080";

const METHOD = {
  PUT(data) {
    return {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    };
  },
  DELETE() {
    return {
      method: "DELETE",
    };
  },
  POST(data) {
    return {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data),
    };
  }
};

const api = (() => {
  const request = (uri, config) => fetch(uri, config);

  const station = {
    getAll() {
      return request(`/stations`);
    },
    getById(id) {
      return request(`/stations/${id}`);
    },
    create(stationRequest) {
      return request(`/stations`, METHOD.POST(stationRequest));
    },
    update(stationRequest, id) {
      return request(`/stations/${id}`, METHOD.PUT(stationRequest));
    },
    delete(id) {
      return request(`/stations/${id}`, METHOD.DELETE());
    }
  };

  const line = {
    getAll() {
      return request(`/lines`);
    },
    getById(id) {
      return request(`/lines/${id}`);
    },
    create(lineRequest) {
      return request(`/lines`, METHOD.POST(lineRequest));
    },
    update(lineRequest, id) {
      return request(`/lines/${id}`, METHOD.PUT(lineRequest));
    },
    delete(id) {
      return request(`/lines/${id}`, METHOD.DELETE());
    }
  };

  const lineStation = {
    getAll() {
      return request('/lines/detail')
    },
    getById(lineId) {
      return request(`/lines/${lineId}/detail`);
    },
    create(lineId, lineStationRequest) {
      return request(`/lines/${lineId}/stations`, METHOD.POST(lineStationRequest));
    },
    delete(lineId, stationId) {
      return request(`/lines/${lineId}/stations/${stationId}`, METHOD.DELETE());
    }
  }

  return {
    station,
    line,
    lineStation,
  };
})();

export default api;