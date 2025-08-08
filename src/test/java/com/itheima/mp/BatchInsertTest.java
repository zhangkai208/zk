package com.itheima.mp;

import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BatchInsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSmallBatchInsert() {
        // 小规模测试，插入100条数据
        System.out.println("开始小规模测试，插入100条数据...");
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            users.add(buildUser(i));
        }
        
        userMapper.batchInsert(users);
        System.out.println("小规模测试完成，插入 " + users.size() + " 条数据");
    }

    @Test
    public void testBatchInsert() {
        long startTime = System.currentTimeMillis();
        
        // 生成十万条数据
        System.out.println("开始生成十万条用户数据...");
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100000; i++) {
            users.add(buildUser(i));
        }
        System.out.println("数据生成完成，共 " + users.size() + " 条");

        // 分批插入，每批1000条
        int batchSize = 1000;
        int totalBatches = (int) Math.ceil((double) users.size() / batchSize);
        System.out.println("开始批量插入，每批 " + batchSize + " 条，共 " + totalBatches + " 批");
        
        for (int i = 0; i < users.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, users.size());
            List<User> batch = users.subList(i, endIndex);
            
            long batchStartTime = System.currentTimeMillis();
            userMapper.batchInsert(batch);
            long batchEndTime = System.currentTimeMillis();
            
            int currentBatch = (i / batchSize) + 1;
            System.out.println("第 " + currentBatch + "/" + totalBatches + " 批插入完成，" +
                    "本批 " + batch.size() + " 条，耗时 " + (batchEndTime - batchStartTime) + "ms，" +
                    "已插入 " + endIndex + "/" + users.size() + " 条");
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("批量插入完成！");
        System.out.println("总共插入: " + users.size() + " 条数据");
        System.out.println("总耗时: " + totalTime + "ms (" + (totalTime / 1000.0) + "秒)");
        System.out.println("平均速度: " + (users.size() / (totalTime / 1000.0)) + " 条/秒");
    }

    @Test
    public void testInsert100KRecords() {
        System.out.println("=== 开始插入十万条数据测试 ===");
        long startTime = System.currentTimeMillis();
        
        // 先清空表（可选）
        // userMapper.delete(null); // 注意：这会删除所有数据
        
        // 生成十万条数据
        System.out.println("正在生成十万条用户数据...");
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100000; i++) {
            users.add(buildUser(i));
        }
        System.out.println("数据生成完成，共 " + users.size() + " 条");

        // 分批插入，每批1000条
        int batchSize = 1000;
        int totalBatches = (int) Math.ceil((double) users.size() / batchSize);
        System.out.println("开始批量插入，每批 " + batchSize + " 条，共 " + totalBatches + " 批");
        
        int actualInserted = 0;
        for (int i = 0; i < users.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, users.size());
            List<User> batch = users.subList(i, endIndex);
            
            long batchStartTime = System.currentTimeMillis();
            userMapper.batchInsert(batch);
            long batchEndTime = System.currentTimeMillis();
            
            actualInserted += batch.size();
            int currentBatch = (i / batchSize) + 1;
            System.out.println("第 " + currentBatch + "/" + totalBatches + " 批插入完成，" +
                    "本批 " + batch.size() + " 条，耗时 " + (batchEndTime - batchStartTime) + "ms，" +
                    "累计已插入 " + actualInserted + "/" + users.size() + " 条");
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        // 验证插入结果
        long count = userMapper.selectCount(null);
        System.out.println("=== 插入结果验证 ===");
        System.out.println("期望插入: " + users.size() + " 条");
        System.out.println("实际插入: " + actualInserted + " 条");
        System.out.println("数据库总记录数: " + count + " 条");
        System.out.println("总耗时: " + totalTime + "ms (" + (totalTime / 1000.0) + "秒)");
        System.out.println("平均速度: " + (users.size() / (totalTime / 1000.0)) + " 条/秒");
        
        if (count >= users.size()) {
            System.out.println("✅ 十万条数据插入成功！");
        } else {
            System.out.println("❌ 数据插入不完整，请检查数据库配置和SQL语句");
        }
    }

    @Test
    public void testCountRecords() {
        // 验证数据库中的记录数
        long count = userMapper.selectCount(null);
        System.out.println("=== 数据库记录数验证 ===");
        System.out.println("当前数据库总记录数: " + count + " 条");
        
        if (count >= 100000) {
            System.out.println("✅ 数据库中确实有十万条以上的记录");
        } else {
            System.out.println("❌ 数据库记录数不足，当前只有 " + count + " 条");
        }
        
        // 查询最后几条记录
        List<User> lastUsers = userMapper.selectList(null);
        if (lastUsers.size() > 0) {
            System.out.println("最后一条记录ID: " + lastUsers.get(lastUsers.size() - 1).getId());
            System.out.println("最后一条记录用户名: " + lastUsers.get(lastUsers.size() - 1).getUsername());
        }
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user" + i);
        user.setPassword("123");
        user.setPhone("" + (18688190000L + i));
        user.setBalance(2000);
        user.setInfo("{\"age\": 24, \"intro\":\"英文老师\", \"gender\": \"female\"}");
        user.setStatus(1); // 1-正常状态
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }
} 