package com.workspace.server.dao

import com.workspace.server.model.GoodsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

/**
 * Created by CHENMA11 on 12/29/2017.
 */
@Transactional
public interface GoodsDao extends CrudRepository<GoodsEntity, Long> {
    //save,delete,deleteAll,findOne和findAll.
    public  GoodsEntity findByGId(int Id);
    public  GoodsEntity findAll(GoodsEntity);
    public  GoodsEntity findByGUrl(String url);

//    GoodsEntity save(GoodsEntity entity);//保存单个实体
//
//    GoodsEntity findOne(int id);//根据id查找实体
//    boolean exists(int id);//根据id判断实体是否存在
//    List<GoodsEntity> findAll();//查询所有实体,不用或慎用!
//    long count();//查询实体数量
//    void delete(int id);//根据Id删除实体
//    void delete(GoodsEntity entity);//删除一个实体
//    void deleteAll();//删除所有实体,不用或慎用!

}