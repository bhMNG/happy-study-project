package com.happystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happystudy.dao.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper dao;
}
