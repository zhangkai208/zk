# TypeScript 学习

Java 后端转 TS 的学习仓库。核心策略：**用 Java 已有的概念做锚点，主攻 TS 独有的思维方式**。

## 运行方式

```bash
npx tsx 01-basics/hello.ts    # 直接运行任意 .ts 文件
npx tsc --noEmit              # 全项目类型检查（相当于"编译看报错"）
```

## 学习路线（跟着 Java 背景 定制）

- [ ] **01-basics** — 基本类型：TS 的 `String/int/boolean` 叫法不同、玩法相通
- [ ] **02-structural** — 结构化类型：TS 没有"实现接口"的仪式感，长得像就是兼容（Java 背景第一观念冲击）
- [ ] **03-unions** — 联合类型与收窄：`A | B` + `typeof`/`in` 收窄，**Java 里不存在的东西，TS 的灵魂**
- [ ] **04-generics** — 泛型：会 Java 泛型就懂一半，重点是约束与推断差异
- [ ] **05-oop** — class / abstract / 访问修饰符：老本行，注意参数属性等 TS 特有语法
- [ ] **06-async** — Promise / async-await：对照 `CompletableFuture` 学
- [ ] **07-modules** — ES Module 与 npm：对照 Maven 坐标理解包管理
- [ ] **08-mini-project** — 小实战：CLI 小工具或 Express 接口（方向到时候定）

## Java 背景三大坑（提前打预防针）

1. **类型在运行时消失**：TS 类型只存在于编译期，运行时就是 JS。Java 的反射/泛型擦除思维在这里要反着来。
2. **没有方法重载**：不能像 Java 那样同名不同参声明两个实现，用联合参数或 `?` 可选参数替代。
3. **`==` 的坑**：JS 的 `==` 会隐式转换，一律用 `===`。

## 进度日志

- 2026-11-03 环境搭建完成（Node 22 + TypeScript + tsx），路线图制定
