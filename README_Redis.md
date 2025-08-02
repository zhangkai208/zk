# Redis 集成说明

## 概述

本项目已成功集成Redis，实现了以下功能：

### 1. 核心功能

#### 1.1 缓存服务
- **文章缓存**: 文章列表和详情缓存，提高查询性能
- **分类缓存**: 分类列表缓存，减少数据库查询
- **自动缓存管理**: 数据更新时自动清除相关缓存

#### 1.2 会话管理
- **Token存储**: 用户登录token存储在Redis中
- **自动过期**: Token设置1小时自动过期
- **安全退出**: 修改密码时自动清除token

#### 1.3 分布式锁
- **并发控制**: 防止并发操作导致的数据不一致
- **原子操作**: 使用Lua脚本确保操作的原子性

### 2. 配置说明

#### 2.1 Redis配置 (application.yml)
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      lettuce:
        pool:
          max-active: 8
          max-idle: 8
          min-idle: 0
          max-wait: -1ms
      timeout: 5000ms
      connect-timeout: 5000ms
```

#### 2.2 缓存过期时间
- **文章缓存**: 1小时 (3600秒)
- **分类缓存**: 2小时 (7200秒)
- **用户Token**: 1小时 (3600秒)

### 3. 核心类说明

#### 3.1 RedisConfig
- 配置RedisTemplate
- 设置序列化方式
- 配置连接池

#### 3.2 RedisUtil
- 提供基础的Redis操作方法
- 支持设置、获取、删除、过期时间等操作

#### 3.3 CacheService
- 缓存服务接口
- 定义文章和分类的缓存操作

#### 3.4 CacheServiceImpl
- 缓存服务实现
- 实现缓存策略和缓存管理

#### 3.5 DistributedLockUtil
- 分布式锁工具类
- 防止并发问题

### 4. API接口

#### 4.1 Redis管理接口 (/redis)
- `GET /redis/status` - 获取Redis状态
- `DELETE /redis/clear-all` - 清除所有缓存
- `DELETE /redis/clear-article` - 清除文章缓存
- `DELETE /redis/clear-category` - 清除分类缓存
- `POST /redis/set` - 设置缓存
- `GET /redis/get` - 获取缓存
- `DELETE /redis/delete` - 删除缓存
- `GET /redis/exists` - 检查缓存是否存在
- `GET /redis/expire` - 获取缓存过期时间

### 5. 使用示例

#### 5.1 缓存文章列表
```java
// 自动缓存，无需手动调用
PageBean<Article> articles = articleService.list(pageNum, pageSize, categoryId, state);
```

#### 5.2 缓存文章详情
```java
// 自动缓存，无需手动调用
Article article = articleService.findById(id);
```

#### 5.3 使用分布式锁
```java
@Autowired
private DistributedLockUtil lockUtil;

String lockKey = "article:update:" + articleId;
String requestId = UUID.randomUUID().toString();

if (lockUtil.tryLock(lockKey, requestId)) {
    try {
        // 执行更新操作
        articleService.update(article);
    } finally {
        lockUtil.releaseLock(lockKey, requestId);
    }
}
```

### 6. 性能优化

#### 6.1 缓存策略
- **读取优先**: 先查缓存，缓存未命中再查数据库
- **写入清除**: 数据更新时自动清除相关缓存
- **过期策略**: 设置合理的过期时间，避免数据过期

#### 6.2 连接池配置
- **最大连接数**: 8个
- **最大空闲连接**: 8个
- **最小空闲连接**: 0个
- **连接超时**: 5秒

### 7. 监控和维护

#### 7.1 缓存监控
- 通过 `/redis/status` 接口监控Redis连接状态
- 通过 `/redis/exists` 接口检查缓存是否存在
- 通过 `/redis/expire` 接口查看缓存过期时间

#### 7.2 缓存清理
- 自动清理: 数据更新时自动清除相关缓存
- 手动清理: 通过API接口手动清除缓存
- 全部清理: 清除所有缓存

### 8. 注意事项

#### 8.1 数据一致性
- 缓存更新策略采用"先更新数据库，再清除缓存"
- 使用分布式锁防止并发更新问题
- 设置合理的缓存过期时间

#### 8.2 内存管理
- 监控Redis内存使用情况
- 定期清理过期缓存
- 避免缓存雪崩和缓存穿透

#### 8.3 错误处理
- Redis连接异常时的降级处理
- 缓存操作失败时的日志记录
- 提供缓存开关，可临时关闭缓存功能

### 9. 部署要求

#### 9.1 Redis服务
- Redis版本: 6.0+
- 内存: 建议2GB+
- 持久化: 开启RDB和AOF

#### 9.2 应用配置
- 确保Redis服务正常运行
- 检查网络连接
- 验证配置文件正确性

### 10. 故障排查

#### 10.1 常见问题
1. **Redis连接失败**: 检查Redis服务状态和网络连接
2. **缓存未生效**: 检查缓存配置和序列化方式
3. **内存不足**: 增加Redis内存或清理过期数据
4. **性能问题**: 优化缓存策略和连接池配置

#### 10.2 日志查看
```yaml
logging:
  level:
    com.zk: debug
    org.springframework.data.redis: debug
```

通过以上配置，您的项目已经成功集成了Redis，实现了高效的缓存管理和会话控制功能。 