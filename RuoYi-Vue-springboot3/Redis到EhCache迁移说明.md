# Redis到EhCache迁移说明

## 已完成的修改

### 1. 依赖配置修改
- ✅ 修改了 `ruoyi-common/pom.xml`，移除了Redis相关依赖，添加了EhCache依赖
- ✅ 修改了 `application.yml`，移除了Redis配置，添加了EhCache配置

### 2. 配置文件创建
- ✅ 创建了 `ehcache.xml` 配置文件，定义了各种缓存策略
- ✅ 重命名并修改了 `RedisConfig.java` 为 `EhCacheConfig.java`

### 3. 缓存工具类替换
- ✅ 创建了新的 `EhCacheUtil.java` 工具类替换 `RedisCache.java`
- ✅ 删除了旧的 `RedisCache.java` 和 `FastJson2JsonRedisSerializer.java`

### 4. 核心服务类更新
- ✅ 更新了 `SysConfigServiceImpl.java`，替换了所有Redis缓存调用
- ✅ 更新了 `TokenService.java`，替换了所有Redis缓存调用

## 需要手动完成的剩余工作

### 1. 更新其他服务类
以下文件需要手动更新，将 `RedisCache` 替换为 `EhCacheUtil`，并添加缓存名称参数：

#### 需要更新的文件：
1. `ruoyi-framework/src/main/java/com/ruoyi/framework/web/service/SysRegisterService.java`
2. `ruoyi-framework/src/main/java/com/ruoyi/framework/web/service/SysPasswordService.java`
3. `ruoyi-framework/src/main/java/com/ruoyi/framework/interceptor/impl/SameUrlDataInterceptor.java`
4. `ruoyi-admin/src/main/java/com/ruoyi/web/controller/monitor/SysUserOnlineController.java`
5. `ruoyi-common/src/main/java/com/ruoyi/common/utils/DictUtils.java`

#### 更新步骤：
1. 将 `import com.ruoyi.common.core.redis.RedisCache;` 替换为 `import com.ruoyi.common.core.cache.EhCacheUtil;`
2. 将 `@Autowired private RedisCache redisCache;` 替换为 `@Autowired private EhCacheUtil ehCacheUtil;`
3. 将所有 `redisCache.xxx()` 调用替换为 `ehCacheUtil.xxx(cacheName, ...)` 调用

### 2. 缓存名称映射
根据 `CacheConstants.java` 中的常量，需要为不同的缓存操作指定正确的缓存名称：

- `LOGIN_TOKEN_KEY` → `"login_tokens"`
- `CAPTCHA_CODE_KEY` → `"captcha_codes"`
- `SYS_CONFIG_KEY` → `"sys_config"`
- `SYS_DICT_KEY` → `"sys_dict"`
- `REPEAT_SUBMIT_KEY` → `"repeat_submit"`
- `RATE_LIMIT_KEY` → `"rate_limit"`
- `PWD_ERR_CNT_KEY` → `"pwd_err_cnt"`

### 3. 特殊处理
- **限流功能**：原来的Redis限流脚本需要重新实现，因为EhCache不支持Lua脚本
- **keys操作**：EhCache不支持keys操作，需要使用其他方式实现
- **过期时间**：EhCache的过期时间在配置文件中设置，不支持动态设置

### 4. 测试验证
完成所有修改后，需要测试以下功能：
- ✅ 用户登录和Token管理
- ✅ 系统参数缓存
- ✅ 字典数据缓存
- ✅ 验证码功能
- ✅ 防重提交功能
- ✅ 限流功能
- ✅ 密码错误次数统计

## 注意事项

1. **性能差异**：EhCache是本地缓存，性能比Redis更好，但不支持分布式
2. **数据一致性**：多实例部署时，EhCache无法保证数据一致性
3. **内存管理**：需要合理配置EhCache的内存使用，避免内存溢出
4. **监控**：需要添加EhCache的监控和统计功能

## 配置文件说明

`ehcache.xml` 中定义了以下缓存：
- `login_tokens`: 登录用户Token缓存，30分钟过期
- `captcha_codes`: 验证码缓存，5分钟过期
- `sys_config`: 系统参数缓存，1小时过期
- `sys_dict`: 字典数据缓存，1小时过期
- `repeat_submit`: 防重提交缓存，10秒过期
- `rate_limit`: 限流缓存，1分钟过期
- `pwd_err_cnt`: 密码错误次数缓存，10分钟过期

可以根据实际需求调整这些配置。
