package com.workspace.server.service

import com.workspace.server.dao.GoodsDao
import com.workspace.server.model.GoodsEntity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
class GoodsSericve  {
        @Autowired
        private GoodsDao employeeRespository;


        @Transactional
        public void save(GoodsEntity){
            employeeRespository.save(GoodsEntity);
        }
}
