package com.perry.infrastructure.client;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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

}
