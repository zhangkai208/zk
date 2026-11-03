// 01-basics 第1课：基本类型 —— 从 Java 视角看 TS
// 运行: npx tsx 01-basics/hello.ts

// ===== 1. 声明变量：let/const 对应 Java 的局部变量，类型写在名字后面 =====
const username: string = "zhangkai"; // const ≈ Java 的 final
let loginCount: number = 3; // JS 不区分 int/long/double，统统是 number
let isVip: boolean = true;

console.log(`用户: ${username}, 登录次数: ${loginCount}, VIP: ${isVip}`);

// ===== 2. 类型可以省略 —— 编译器会推断（Type Inference）=====
const age = 25; // 编译器知道这是 number，不用写
// age = "25"; // ← 试试取消注释，npx tsc --noEmit 会报错，和 Java 一样编译期拦截

// ===== 3. 数组：TS 的"泛型数组"写法更轻 =====
const roles: string[] = ["admin", "user"]; // 等价 Java 的 List<String>（但只是声明，不是对象）
const scores: Array<number> = [90, 85]; // 另一种等价写法

// ===== 4. 对象类型：直接内联声明，不需要先定义类 =====
const user: { id: number; name: string } = { id: 1, name: "张凯" };
console.log(user.name);

// ===== 5. 函数：参数和返回值都有类型 =====
function add(a: number, b: number): number {
  return a + b;
}

// 箭头函数（Java 的 Lambda）: (参数) => 返回值
const multiply = (a: number, b: number): number => a * b;

console.log(add(1, 2), multiply(3, 4));

// ===== 6. any：关掉类型检查的逃生门（学习期禁用，生产环境也是）=====
// let anything: any = "随便什么都行";  // any 就像把编译器闭嘴了

// unknown 是更负责任的 any：用之前必须收窄类型，后面课程细讲
function safeLog(value: unknown): void {
  if (typeof value === "string") {
    console.log("字符串:", value.toUpperCase()); // 收窄后才能调用 string 方法
  } else {
    console.log("不是字符串:", value);
  }
}
safeLog("hello");
safeLog(42);

// ===== 动手练习 =====
// 1. 声明一个数组，存 3 个 { name: string; score: number } 的对象，打印平均分
// 2. 写一个函数 greeting(name: string, title?: string): string
//    title 是可选参数（对应 Java 的重载，但 TS 用 ? 一个函数搞定）
//    有 title 返回 "张老师"，没有返回 "张"
