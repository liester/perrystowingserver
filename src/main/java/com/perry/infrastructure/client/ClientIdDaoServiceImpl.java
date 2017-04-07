package com.perry.infrastructure.client;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.perry.domain.ClientId;

import rowmappers.ClientIdRowMapper;

@Named
public class ClientIdDaoServiceImpl implements ClientIdDaoService {

	@Inject
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<ClientId> getAll() {
		String sql = "select * from clients";
		List<ClientId> clientIds = namedParameterJdbcTemplate.query(sql, new ClientIdRowMapper());
		return clientIds;
	}

	@Override
	public List<ClientId> updateAll(List<ClientId> clientIds) {
		for (ClientId clientId : clientIds) {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("clientId", clientId.getClientId());
			params.addValue("role", clientId.getRole());
			String sql = "update clients set role = :role where client_id = :clientId";
			namedParameterJdbcTemplate.update(sql, params);
		}
		List<ClientId> clientIdReturnList = getAll();
		return clientIdReturnList;
	}

	@Override
	public void deleteById(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		String sql = "delete * from clients where id = :id";
		namedParameterJdbcTemplate.update(sql, params);
	}

}
