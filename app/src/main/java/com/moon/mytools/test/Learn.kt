package com.moon.mytools.test

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.createCoroutine

/**
 * Date: 2023/6/29 11:04
 * Author: Moon
 * Desc:
 */

//val ViewGroup.firstView: View
//    get() = getChildAt(0)

//class User(var name: String, var age: Int) {
//    override fun toString(): String {
//        return "User(name='$name', age=$age)"
//    }
//}
//
//fun upFun1(name: String, age: Int): Float {
//    return 88f
//}
//
////testUpFun1 接收的参数为函数类型：(String, Int)->String
//fun testUpFun1(getScore: (String, Int) -> Float) {
//    var score = getScore("fish", 18)
//    println("student score:$score")
//}
//
//inline fun inlineFun4(noinline block: (Int) -> String): String {
//    println("execute fun4")
//    //编译错误
//    return inlineFun5(block)
//}
//
//fun inlineFun5(block: (Int) -> String): String {
//    return block(3)
//}
//
//inline fun inlineFun6(block: (Int) -> String): String {
//    println("execute fun6")
//    return block(3)
//}
//
//fun testReturn(): String {
//    var str = inlineFun6 {
//        if (it > 3) {
//            ">3"
//        } else {
//            "<=3"
//        }
//        return "fish"
//    }
//    println("execute inlineFun6 str:$str")
//    return "fish"
//}
//
//inline fun inlineFun7(crossinline block: (Int) -> String) {
//    println("execute fun7")
//    Runnable {
//        block(3)
//    }
//}
//
//fun testReturn2() {
//    var str = inlineFun7 {
//        if (it > 3) {
//            ">3"
//        } else {
//            "<=3"
//        }
//    }
//    println("execute inlineFun7 str:$str")
//}
//
//inline fun message(crossinline block: () -> Unit) {
//    block()
//    println("-----")
//}
//
//fun test() {
//    message {
//        println("Hello")
//        return@message
//    }
//    println("World")
//}

//interface Calculator {
//    fun calculate(a: Int, b: Int): Int
//}
//
//inline fun test(crossinline block: (Int, Int) -> Int) {
//    val c = object : Calculator {
//        override fun calculate(a: Int, b: Int): Int = block(a, b)
//    }
//    println(c.calculate(3, 7))
//}
//
//inline fun message(block: () -> Unit) {
//    block()
//    println("-----")
//}
//
//fun test(): Boolean {
//    message {
//        println("Hello")
//        return false
//    }
//    println("World")
//    return true
//}
//
//class StudentInfo {
//    //姓名
//    var name: String? = "Fish"
//    var alias: String? = "小鱼人"
//
//    //省份
//    var province: String? = "北京"
//
//    //年龄
//    var age: Int? = 18
//
//    //性别
//    var isBoy: Boolean = true
//
//    //分数
//    var score: Float = 88f
//
//    override fun toString(): String {
//        return "StudentInfo(name=$name, alias=$alias, province=$province, age=$age, isBoy=$isBoy, score=$score)"
//    }
//}
//
//fun testLet(studentInfo: StudentInfo?) {
//    val ret = studentInfo?.let {
//        it.name = "777"
//        "1717"
//    }
//    println("letRet:$ret")
//}
//
//fun testAlso(studentInfo: StudentInfo) {
//    val ret = studentInfo?.also {
//        it.name = "ALSO"
//    }
//    println("alsoRet:$ret")
//}
//
//fun testWith(studentInfo: StudentInfo) {
//    var ret = with(studentInfo){
//        name = "WITH"
//        "3737"
//    }
//
//    println("withRet:$ret, ${studentInfo.name}")
//}
//
//fun testRun(studentInfo: StudentInfo){
//    var ret = studentInfo.run {
//        name = "RUN"
//        "4747"
//    }
//
//    println("runRet:$ret, ${studentInfo.name}")
//}
//
//fun testApply(studentInfo: StudentInfo){
//    var ret = studentInfo.apply {
//        name = "APPLY"
//        "5757"
//    }
//
//    println("runRet:$ret, ${studentInfo.name}")
//}

//inline fun dodo(action: () -> Unit) {
//    println("startTime::${System.currentTimeMillis()}")
//
//    action()
//
//    println("endTime::${System.currentTimeMillis()}")
//}

//class ObjTest {
//
//    open class A {
//        val xA = "A"
//        open fun fromA() {}
//    }
//
//    interface B
//
//    private val objfun1 = object {
//        val x1 = 1
//        fun do1() {
//            println("do1")
//        }
//    }
//
//    fun objfun2() = object {
//        val x2 = 2
//        fun do2() {
//            println("do2")
//        }
//    }
//
//    fun objfun3() = object : A() {
//        val x3 = 3
//        override fun fromA() {
//            super.fromA()
//            println("fromA")
//        }
//    }
//
//    fun objfun4(): B = object : A(), B {
//
//    }
//
//    fun test() {
//        objfun1.do1()
//        objfun1.x1
////        objfun2().do2() 报错
//        objfun3().fromA()
//        //objfun3().x3 报错
//        val objfun4 = objfun4()
//
//        OutClass.companionHello()
//        OutClass.InnerClass().innerHello()
//        OutClass.InnerClass.innnerCompanionHello()
//    }
//}

//class OutClass {
//    companion object {
//        fun companionHello(): Int {
//            return 1
//        }
//    }
//
//    class InnerClass {
//        fun innerHello(): Int {
//            return 2
//        }
//
//        companion object {
//            fun innnerCompanionHello() {
//                println("innnerCompanionHello")
//            }
//        }
//    }
//}

//@Retention(AnnotationRetention.RUNTIME)
//@Target(AnnotationTarget.CLASS)
//annotation class Api(val name: String)
//
//@Api("http://www.baidu.com")
//interface RetroApi {
//
//}
//
//data class DataFather(val name: String)
//
//@JvmInline
//value class BoxInt(val int: Int)
//
//interface Ia<in P, out R> {
//    fun com(p: P): R;
//}
//
//class AImpl : Ia<Int, Number> {
//    override fun com(p: Int): Number {
//        return 1
//    }
//}
//
//fun demo(x: Comparable<Number>) {
//    x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的子类型
//    // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
//    val y: Comparable<Double> = x
//}

interface IProxyKt {
    fun setName(name: String)
    fun getName(): String
}

fun main(args: Array<String>) {
//    var ret = Proxy.newProxyInstance(
//        LearnJava::class.java.classLoader,
//        arrayOf(IProxyKt::class.java),
//        object : InvocationHandler {
//            override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
//                println(method!!.name)
//                println(args?.joinToString())
//                return when (method.name) {
//                    "getName" -> "Hello"
//                    else -> ""
//                }
//            }
//        }) as IProxyKt
//
//    ret.setName("123")
//    println(ret.getName())


}

class Aa(val name:String?){
    fun printStr(){
        println(name)
    }
}

fun pln() {
    println("--------------------")
}
