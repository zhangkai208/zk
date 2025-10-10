package com.ruoyi.web.controller.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.cache.EhCacheUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysCache;

/**
 * 缓存监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController
{
    @Autowired
    private EhCacheUtil ehCacheUtil;

    private final static List<SysCache> caches = new ArrayList<SysCache>();
    {
        caches.add(new SysCache("login_tokens", "用户信息"));
        caches.add(new SysCache("sys_config", "配置信息"));
        caches.add(new SysCache("sys_dict", "数据字典"));
        caches.add(new SysCache("captcha_codes", "验证码"));
        caches.add(new SysCache("repeat_submit", "防重提交"));
        caches.add(new SysCache("rate_limit", "限流处理"));
        caches.add(new SysCache("pwd_err_cnt", "密码错误次数"));
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        // EhCache不支持info命令，返回基本信息
        Map<String, Object> result = new HashMap<>(3);
        result.put("info", "EhCache本地缓存");
        result.put("dbSize", "N/A");
        result.put("commandStats", new ArrayList<>());
        return AjaxResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getNames")
    public AjaxResult cache()
    {
        return AjaxResult.success(caches);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getKeys/{cacheName}")
    public AjaxResult getCacheKeys(@PathVariable String cacheName)
    {
        // EhCache不支持keys操作，返回空集合
        return AjaxResult.success(new TreeSet<>());
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public AjaxResult getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey)
    {
        Object cacheValue = ehCacheUtil.getCacheObject(cacheName, cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, String.valueOf(cacheValue));
        return AjaxResult.success(sysCache);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheName/{cacheName}")
    public AjaxResult clearCacheName(@PathVariable String cacheName)
    {
        ehCacheUtil.clearCache(cacheName);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public AjaxResult clearCacheKey(@PathVariable String cacheKey)
    {
        // EhCache不支持按key删除，需要指定缓存名称
        // 这里尝试从所有缓存中删除
        for (SysCache cache : caches) {
            ehCacheUtil.deleteObject(cache.getCacheName(), cacheKey);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheAll")
    public AjaxResult clearCacheAll()
    {
        // 清空所有缓存
        for (SysCache cache : caches) {
            ehCacheUtil.clearCache(cache.getCacheName());
        }
        return AjaxResult.success();
    }
}
