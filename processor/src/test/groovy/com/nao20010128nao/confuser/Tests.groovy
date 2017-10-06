package com.nao20010128nao.confuser

import com.google.testing.compile.Compilation
import com.google.testing.compile.Compiler
import com.google.testing.compile.JavaFileObjects
import junit.framework.TestCase
import org.junit.Test

class Tests extends TestCase {
    @Test
    void testSimpleGeneration(){
        def compiler=Compiler.javac().withProcessors(new ConfusionProcessor())

        def input='''
@com.nao20010128nao.confuser.WillConfuse(value="TestOutput",randomSeed="It's a small world",nest=10)
class SimpleGeneration{

}
'''
        def output= compiler.compile(JavaFileObjects.forSourceString('SimpleGeneration',input))
        try{
            assert output.status()==Compilation.Status.SUCCESS
        }catch (Throwable e){
            println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
            throw e
        }

        /*println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
        return*/

        def outputSource=output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)?.toString()?.trim()
        def expected='''
/* AUTO GENERATED FILE. DO NOT CHANGE. */
public /* no extend limitation */ class TestOutput extends _748C6623F9EC3F7D95EE0450FFD17057{
}
abstract class _748C6623F9EC3F7D95EE0450FFD17057 extends _E501B423A38E36059FCC47D665E624CC{
}
abstract class _E501B423A38E36059FCC47D665E624CC extends _71C51544BFA133BD80A3B73E3D00AA7C{
}
abstract class _71C51544BFA133BD80A3B73E3D00AA7C extends _BF1CED1834473DCB8A194820A423F04F{
}
abstract class _BF1CED1834473DCB8A194820A423F04F extends _EC49E9D183A631059D899154EED49AC2{
}
abstract class _EC49E9D183A631059D899154EED49AC2 extends _91AA0C350EBD39FD8F6A48684C5DFC2B{
}
abstract class _91AA0C350EBD39FD8F6A48684C5DFC2B extends _12D4AD57E6FA34ABBA35A95EDCF3B0D4{
}
abstract class _12D4AD57E6FA34ABBA35A95EDCF3B0D4 extends _00D1E0086AD13CA7B82B1F710666AEFD{
}
abstract class _00D1E0086AD13CA7B82B1F710666AEFD extends _401F594192A83413B2E9E4B617D3DE7E{
}
abstract class _401F594192A83413B2E9E4B617D3DE7E extends _C19C67974B9F368088FEF52A93433930{
}
abstract class _C19C67974B9F368088FEF52A93433930 extends SimpleGeneration{
}
'''.trim()
        assert outputSource==expected
    }

    @Test
    void testNotOverriddenConstructorsGeneration(){
        def compiler=Compiler.javac().withProcessors(new ConfusionProcessor())

        def input='''
@com.nao20010128nao.confuser.WillConfuse(value="TestOutput",randomSeed="Senbonzakura",nest=10)
class NotOverriddenConstructorsGeneration{
    NotOverriddenConstructorsGeneration(){
        System.out.println("Say hello for Annotation Processor!");
    }
}
'''
        def output= compiler.compile(JavaFileObjects.forSourceString('NotOverriddenConstructorsGeneration',input))
        try{
            assert output.status()==Compilation.Status.SUCCESS
        }catch (Throwable e){
            println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
            throw e
        }

        def outputSource=output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)?.toString()?.trim()
        def expected='''
/* AUTO GENERATED FILE. DO NOT CHANGE. */
public /* no extend limitation */ class TestOutput extends _74F21FE9BB883E6B8B62174D62A9B731{
}
abstract class _74F21FE9BB883E6B8B62174D62A9B731 extends _1E20B3C553463DAFBB03A576D7C1BE22{
}
abstract class _1E20B3C553463DAFBB03A576D7C1BE22 extends _6D7715C254653001B62659ADDE01492A{
}
abstract class _6D7715C254653001B62659ADDE01492A extends _C9CA0065B88C357590EAE63373AFC3F2{
}
abstract class _C9CA0065B88C357590EAE63373AFC3F2 extends _C818196699E1305E878324FC634EA0EC{
}
abstract class _C818196699E1305E878324FC634EA0EC extends _4656DFF3B8E631B890126406E4A4203B{
}
abstract class _4656DFF3B8E631B890126406E4A4203B extends _7C44A503AA7C3285916C4D8061E8A72A{
}
abstract class _7C44A503AA7C3285916C4D8061E8A72A extends _7D877C67D69F37DBB6CDB71B4C822712{
}
abstract class _7D877C67D69F37DBB6CDB71B4C822712 extends _667D6ECF95163DC58FE53BC64D6223C8{
}
abstract class _667D6ECF95163DC58FE53BC64D6223C8 extends _9858A2E0AF9D3B78B18248A08337BC0C{
}
abstract class _9858A2E0AF9D3B78B18248A08337BC0C extends NotOverriddenConstructorsGeneration{
}
'''.trim()
        assert outputSource==expected
    }

    @Test
    void testOverriddenConstructorsGeneration(){
        def compiler=Compiler.javac().withProcessors(new ConfusionProcessor())

        def input='''
@com.nao20010128nao.confuser.WillConfuse(value="TestOutput",randomSeed="Senbonzakura",nest=10)
class OverriddenConstructorsGeneration{
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenConstructorsGeneration(){
        System.out.println("Say hello to Annotation Processor!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenConstructorsGeneration(String str){
        System.out.println("Say hello to "+str+"!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenConstructorsGeneration(String[] str){
        System.out.println("Say hello to "+str+"!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenConstructorsGeneration(int[] str){
        System.out.println("Say hello to "+str+"!");
    }
}
'''
        def output= compiler.compile(JavaFileObjects.forSourceString('OverriddenConstructorsGeneration',input))
        try{
            assert output.status()==Compilation.Status.SUCCESS
        }catch (Throwable e){
            println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
            throw e
        }

        /*println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
        return*/

        def outputSource=output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)?.toString()?.trim()
        def expected='''
/* AUTO GENERATED FILE. DO NOT CHANGE. */
public /* no extend limitation */ class TestOutput extends _74F21FE9BB883E6B8B62174D62A9B731{
    public /* no extend limitation */ TestOutput(){
        super();
    }
    public /* no extend limitation */ TestOutput(java.lang.String arg0){
        super(arg0);
    }
    public /* no extend limitation */ TestOutput(java.lang.String[] arg0){
        super(arg0);
    }
    public /* no extend limitation */ TestOutput(int[] arg0){
        super(arg0);
    }
}
abstract class _74F21FE9BB883E6B8B62174D62A9B731 extends _1E20B3C553463DAFBB03A576D7C1BE22{
    _74F21FE9BB883E6B8B62174D62A9B731(){
        super();
    }
    _74F21FE9BB883E6B8B62174D62A9B731(java.lang.String arg0){
        super(arg0);
    }
    _74F21FE9BB883E6B8B62174D62A9B731(java.lang.String[] arg0){
        super(arg0);
    }
    _74F21FE9BB883E6B8B62174D62A9B731(int[] arg0){
        super(arg0);
    }
}
abstract class _1E20B3C553463DAFBB03A576D7C1BE22 extends _6D7715C254653001B62659ADDE01492A{
    _1E20B3C553463DAFBB03A576D7C1BE22(){
        super();
    }
    _1E20B3C553463DAFBB03A576D7C1BE22(java.lang.String arg0){
        super(arg0);
    }
    _1E20B3C553463DAFBB03A576D7C1BE22(java.lang.String[] arg0){
        super(arg0);
    }
    _1E20B3C553463DAFBB03A576D7C1BE22(int[] arg0){
        super(arg0);
    }
}
abstract class _6D7715C254653001B62659ADDE01492A extends _C9CA0065B88C357590EAE63373AFC3F2{
    _6D7715C254653001B62659ADDE01492A(){
        super();
    }
    _6D7715C254653001B62659ADDE01492A(java.lang.String arg0){
        super(arg0);
    }
    _6D7715C254653001B62659ADDE01492A(java.lang.String[] arg0){
        super(arg0);
    }
    _6D7715C254653001B62659ADDE01492A(int[] arg0){
        super(arg0);
    }
}
abstract class _C9CA0065B88C357590EAE63373AFC3F2 extends _C818196699E1305E878324FC634EA0EC{
    _C9CA0065B88C357590EAE63373AFC3F2(){
        super();
    }
    _C9CA0065B88C357590EAE63373AFC3F2(java.lang.String arg0){
        super(arg0);
    }
    _C9CA0065B88C357590EAE63373AFC3F2(java.lang.String[] arg0){
        super(arg0);
    }
    _C9CA0065B88C357590EAE63373AFC3F2(int[] arg0){
        super(arg0);
    }
}
abstract class _C818196699E1305E878324FC634EA0EC extends _4656DFF3B8E631B890126406E4A4203B{
    _C818196699E1305E878324FC634EA0EC(){
        super();
    }
    _C818196699E1305E878324FC634EA0EC(java.lang.String arg0){
        super(arg0);
    }
    _C818196699E1305E878324FC634EA0EC(java.lang.String[] arg0){
        super(arg0);
    }
    _C818196699E1305E878324FC634EA0EC(int[] arg0){
        super(arg0);
    }
}
abstract class _4656DFF3B8E631B890126406E4A4203B extends _7C44A503AA7C3285916C4D8061E8A72A{
    _4656DFF3B8E631B890126406E4A4203B(){
        super();
    }
    _4656DFF3B8E631B890126406E4A4203B(java.lang.String arg0){
        super(arg0);
    }
    _4656DFF3B8E631B890126406E4A4203B(java.lang.String[] arg0){
        super(arg0);
    }
    _4656DFF3B8E631B890126406E4A4203B(int[] arg0){
        super(arg0);
    }
}
abstract class _7C44A503AA7C3285916C4D8061E8A72A extends _7D877C67D69F37DBB6CDB71B4C822712{
    _7C44A503AA7C3285916C4D8061E8A72A(){
        super();
    }
    _7C44A503AA7C3285916C4D8061E8A72A(java.lang.String arg0){
        super(arg0);
    }
    _7C44A503AA7C3285916C4D8061E8A72A(java.lang.String[] arg0){
        super(arg0);
    }
    _7C44A503AA7C3285916C4D8061E8A72A(int[] arg0){
        super(arg0);
    }
}
abstract class _7D877C67D69F37DBB6CDB71B4C822712 extends _667D6ECF95163DC58FE53BC64D6223C8{
    _7D877C67D69F37DBB6CDB71B4C822712(){
        super();
    }
    _7D877C67D69F37DBB6CDB71B4C822712(java.lang.String arg0){
        super(arg0);
    }
    _7D877C67D69F37DBB6CDB71B4C822712(java.lang.String[] arg0){
        super(arg0);
    }
    _7D877C67D69F37DBB6CDB71B4C822712(int[] arg0){
        super(arg0);
    }
}
abstract class _667D6ECF95163DC58FE53BC64D6223C8 extends _9858A2E0AF9D3B78B18248A08337BC0C{
    _667D6ECF95163DC58FE53BC64D6223C8(){
        super();
    }
    _667D6ECF95163DC58FE53BC64D6223C8(java.lang.String arg0){
        super(arg0);
    }
    _667D6ECF95163DC58FE53BC64D6223C8(java.lang.String[] arg0){
        super(arg0);
    }
    _667D6ECF95163DC58FE53BC64D6223C8(int[] arg0){
        super(arg0);
    }
}
abstract class _9858A2E0AF9D3B78B18248A08337BC0C extends OverriddenConstructorsGeneration{
    _9858A2E0AF9D3B78B18248A08337BC0C(){
        super();
    }
    _9858A2E0AF9D3B78B18248A08337BC0C(java.lang.String arg0){
        super(arg0);
    }
    _9858A2E0AF9D3B78B18248A08337BC0C(java.lang.String[] arg0){
        super(arg0);
    }
    _9858A2E0AF9D3B78B18248A08337BC0C(int[] arg0){
        super(arg0);
    }
}
'''.trim()
        assert outputSource==expected
    }

    @Test
    void testNotOverriddenMethodsGeneration(){
        def compiler=Compiler.javac().withProcessors(new ConfusionProcessor())

        def input='''
@com.nao20010128nao.confuser.WillConfuse(value="TestOutput",randomSeed="Ramen tabetai",nest=10)
class NotOverriddenMethodsGeneration{
    public void method1(){
        System.out.println("Say hello for Annotation Processor!");
    }
    public String method2(){
        return "NotOverriddenMethodsGeneration never writes any methods in child classes.";
    }
}
'''
        def output= compiler.compile(JavaFileObjects.forSourceString('NotOverriddenMethodsGeneration',input))
        try{
            assert output.status()==Compilation.Status.SUCCESS
        }catch (Throwable e){
            println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
            throw e
        }

        /*println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
        return*/

        def outputSource=output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)?.toString()?.trim()
        def expected='''
/* AUTO GENERATED FILE. DO NOT CHANGE. */
public /* no extend limitation */ class TestOutput extends _CEA32AE604253E62A53CC370F28EED57{
}
abstract class _CEA32AE604253E62A53CC370F28EED57 extends _03A8AA36274033D3A0DC7672AD08619E{
}
abstract class _03A8AA36274033D3A0DC7672AD08619E extends _0E9BA472D8B83D8D88B9E4D030D57B3C{
}
abstract class _0E9BA472D8B83D8D88B9E4D030D57B3C extends _66F5FBE37B0F3E0B90D6ABD5D46739C2{
}
abstract class _66F5FBE37B0F3E0B90D6ABD5D46739C2 extends _59A6F96B06863715AFB7340D0006E266{
}
abstract class _59A6F96B06863715AFB7340D0006E266 extends _8A1E7DE9F766357496BAD4FFDB107C46{
}
abstract class _8A1E7DE9F766357496BAD4FFDB107C46 extends _82B411C0964A3D8CA54E0A705237F4FC{
}
abstract class _82B411C0964A3D8CA54E0A705237F4FC extends _452DAF412AA03D2580DB5092ED0EBB40{
}
abstract class _452DAF412AA03D2580DB5092ED0EBB40 extends _8731309513F233759CD8AEEAF108D1E9{
}
abstract class _8731309513F233759CD8AEEAF108D1E9 extends _D04D7CCCCE773FA98BD93471FE92DB59{
}
abstract class _D04D7CCCCE773FA98BD93471FE92DB59 extends NotOverriddenMethodsGeneration{
}
'''.trim()
        assert outputSource==expected
    }

    @Test
    void testOverriddenMethodsGeneration(){
        def compiler=Compiler.javac().withProcessors(new ConfusionProcessor())

        def input='''
@com.nao20010128nao.confuser.WillConfuse(value="TestOutput",randomSeed="Good morning",nest=5)
class OverriddenMethodsGeneration{
    @com.nao20010128nao.confuser.NestedOverridden
    public void method1(){
        System.out.println("Say hello for Annotation Processor!");
    }
    @com.nao20010128nao.confuser.NestedOverridden(returnRequired=true)
    public String method2(){
        return "OverriddenMethodsGeneration writes some methods in child classes.";
    }
}
'''
        def output= compiler.compile(JavaFileObjects.forSourceString('OverriddenMethodsGeneration',input))
        try{
            assert output.status()==Compilation.Status.SUCCESS
        }catch (Throwable e){
            println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
            throw e
        }

        /*println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
        return*/

        def outputSource=output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)?.toString()?.trim()
        def expected='''
/* AUTO GENERATED FILE. DO NOT CHANGE. */
public /* no extend limitation */ class TestOutput extends _CEA32AE604253E62A53CC370F28EED57{
    public /* no override limitation */ void method1(){
        super.method1();
    }
    public /* no override limitation */ java.lang.String method2(){
        return super.method2();
    }
}
abstract class _CEA32AE604253E62A53CC370F28EED57 extends _03A8AA36274033D3A0DC7672AD08619E{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _03A8AA36274033D3A0DC7672AD08619E extends _0E9BA472D8B83D8D88B9E4D030D57B3C{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _0E9BA472D8B83D8D88B9E4D030D57B3C extends _66F5FBE37B0F3E0B90D6ABD5D46739C2{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _66F5FBE37B0F3E0B90D6ABD5D46739C2 extends _59A6F96B06863715AFB7340D0006E266{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _59A6F96B06863715AFB7340D0006E266 extends _8A1E7DE9F766357496BAD4FFDB107C46{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _8A1E7DE9F766357496BAD4FFDB107C46 extends _82B411C0964A3D8CA54E0A705237F4FC{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _82B411C0964A3D8CA54E0A705237F4FC extends _452DAF412AA03D2580DB5092ED0EBB40{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _452DAF412AA03D2580DB5092ED0EBB40 extends _8731309513F233759CD8AEEAF108D1E9{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _8731309513F233759CD8AEEAF108D1E9 extends _D04D7CCCCE773FA98BD93471FE92DB59{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _D04D7CCCCE773FA98BD93471FE92DB59 extends OverriddenMethodsGeneration{
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
'''.trim()
        assert outputSource==expected
    }

    @Test
    void testOverriddenMethodsMixedGeneration(){
        def compiler=Compiler.javac().withProcessors(new ConfusionProcessor())

        def input='''
@com.nao20010128nao.confuser.WillConfuse(value="TestOutput",randomSeed="Ramen tabetai",nest=10)
class OverriddenMethodsMixedGeneration{
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenMethodsMixedGeneration(){
        System.out.println("Say hello to Annotation Processor!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenMethodsMixedGeneration(String str){
        System.out.println("Say hello to "+str+"!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenMethodsMixedGeneration(String[] str){
        System.out.println("Say hello to "+str+"!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    OverriddenMethodsMixedGeneration(int[] str){
        System.out.println("Say hello to "+str+"!");
    }
    @com.nao20010128nao.confuser.NestedOverridden
    public void method1(){
        System.out.println("Say hello for Annotation Processor!");
    }
    @com.nao20010128nao.confuser.NestedOverridden(returnRequired=true)
    public String method2(){
        return "OverriddenMethodsMixedGeneration writes some methods and constructors in child classes.";
    }
}
'''
        def output= compiler.compile(JavaFileObjects.forSourceString('OverriddenMethodsMixedGeneration',input))
        try{
            assert output.status()==Compilation.Status.SUCCESS
        }catch (Throwable e){
            println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
            throw e
        }

        /*println output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)
        return*/

        def outputSource=output.generatedSourceFile('TestOutput').orElse(null)?.getCharContent(true)?.toString()?.trim()
        def expected='''
/* AUTO GENERATED FILE. DO NOT CHANGE. */
public /* no extend limitation */ class TestOutput extends _CEA32AE604253E62A53CC370F28EED57{
    public /* no extend limitation */ TestOutput(){
        super();
    }
    public /* no extend limitation */ TestOutput(java.lang.String arg0){
        super(arg0);
    }
    public /* no extend limitation */ TestOutput(java.lang.String[] arg0){
        super(arg0);
    }
    public /* no extend limitation */ TestOutput(int[] arg0){
        super(arg0);
    }
    public /* no override limitation */ void method1(){
        super.method1();
    }
    public /* no override limitation */ java.lang.String method2(){
        return super.method2();
    }
}
abstract class _CEA32AE604253E62A53CC370F28EED57 extends _03A8AA36274033D3A0DC7672AD08619E{
    _CEA32AE604253E62A53CC370F28EED57(){
        super();
    }
    _CEA32AE604253E62A53CC370F28EED57(java.lang.String arg0){
        super(arg0);
    }
    _CEA32AE604253E62A53CC370F28EED57(java.lang.String[] arg0){
        super(arg0);
    }
    _CEA32AE604253E62A53CC370F28EED57(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _03A8AA36274033D3A0DC7672AD08619E extends _0E9BA472D8B83D8D88B9E4D030D57B3C{
    _03A8AA36274033D3A0DC7672AD08619E(){
        super();
    }
    _03A8AA36274033D3A0DC7672AD08619E(java.lang.String arg0){
        super(arg0);
    }
    _03A8AA36274033D3A0DC7672AD08619E(java.lang.String[] arg0){
        super(arg0);
    }
    _03A8AA36274033D3A0DC7672AD08619E(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _0E9BA472D8B83D8D88B9E4D030D57B3C extends _66F5FBE37B0F3E0B90D6ABD5D46739C2{
    _0E9BA472D8B83D8D88B9E4D030D57B3C(){
        super();
    }
    _0E9BA472D8B83D8D88B9E4D030D57B3C(java.lang.String arg0){
        super(arg0);
    }
    _0E9BA472D8B83D8D88B9E4D030D57B3C(java.lang.String[] arg0){
        super(arg0);
    }
    _0E9BA472D8B83D8D88B9E4D030D57B3C(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _66F5FBE37B0F3E0B90D6ABD5D46739C2 extends _59A6F96B06863715AFB7340D0006E266{
    _66F5FBE37B0F3E0B90D6ABD5D46739C2(){
        super();
    }
    _66F5FBE37B0F3E0B90D6ABD5D46739C2(java.lang.String arg0){
        super(arg0);
    }
    _66F5FBE37B0F3E0B90D6ABD5D46739C2(java.lang.String[] arg0){
        super(arg0);
    }
    _66F5FBE37B0F3E0B90D6ABD5D46739C2(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _59A6F96B06863715AFB7340D0006E266 extends _8A1E7DE9F766357496BAD4FFDB107C46{
    _59A6F96B06863715AFB7340D0006E266(){
        super();
    }
    _59A6F96B06863715AFB7340D0006E266(java.lang.String arg0){
        super(arg0);
    }
    _59A6F96B06863715AFB7340D0006E266(java.lang.String[] arg0){
        super(arg0);
    }
    _59A6F96B06863715AFB7340D0006E266(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _8A1E7DE9F766357496BAD4FFDB107C46 extends _82B411C0964A3D8CA54E0A705237F4FC{
    _8A1E7DE9F766357496BAD4FFDB107C46(){
        super();
    }
    _8A1E7DE9F766357496BAD4FFDB107C46(java.lang.String arg0){
        super(arg0);
    }
    _8A1E7DE9F766357496BAD4FFDB107C46(java.lang.String[] arg0){
        super(arg0);
    }
    _8A1E7DE9F766357496BAD4FFDB107C46(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _82B411C0964A3D8CA54E0A705237F4FC extends _452DAF412AA03D2580DB5092ED0EBB40{
    _82B411C0964A3D8CA54E0A705237F4FC(){
        super();
    }
    _82B411C0964A3D8CA54E0A705237F4FC(java.lang.String arg0){
        super(arg0);
    }
    _82B411C0964A3D8CA54E0A705237F4FC(java.lang.String[] arg0){
        super(arg0);
    }
    _82B411C0964A3D8CA54E0A705237F4FC(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _452DAF412AA03D2580DB5092ED0EBB40 extends _8731309513F233759CD8AEEAF108D1E9{
    _452DAF412AA03D2580DB5092ED0EBB40(){
        super();
    }
    _452DAF412AA03D2580DB5092ED0EBB40(java.lang.String arg0){
        super(arg0);
    }
    _452DAF412AA03D2580DB5092ED0EBB40(java.lang.String[] arg0){
        super(arg0);
    }
    _452DAF412AA03D2580DB5092ED0EBB40(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _8731309513F233759CD8AEEAF108D1E9 extends _D04D7CCCCE773FA98BD93471FE92DB59{
    _8731309513F233759CD8AEEAF108D1E9(){
        super();
    }
    _8731309513F233759CD8AEEAF108D1E9(java.lang.String arg0){
        super(arg0);
    }
    _8731309513F233759CD8AEEAF108D1E9(java.lang.String[] arg0){
        super(arg0);
    }
    _8731309513F233759CD8AEEAF108D1E9(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
abstract class _D04D7CCCCE773FA98BD93471FE92DB59 extends OverriddenMethodsMixedGeneration{
    _D04D7CCCCE773FA98BD93471FE92DB59(){
        super();
    }
    _D04D7CCCCE773FA98BD93471FE92DB59(java.lang.String arg0){
        super(arg0);
    }
    _D04D7CCCCE773FA98BD93471FE92DB59(java.lang.String[] arg0){
        super(arg0);
    }
    _D04D7CCCCE773FA98BD93471FE92DB59(int[] arg0){
        super(arg0);
    }
    public void method1(){
        super.method1();
    }
    public java.lang.String method2(){
        return super.method2();
    }
}
'''.trim()
        assert outputSource==expected
    }
}
