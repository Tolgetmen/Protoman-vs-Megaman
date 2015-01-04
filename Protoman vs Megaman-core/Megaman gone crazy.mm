<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1419841538742" ID="ID_181331312" MODIFIED="1419841618996" STYLE="fork" TEXT="Megaman gone crazy">
<edge COLOR="#808080" STYLE="bezier" WIDTH="thin"/>
<font BOLD="true" NAME="SansSerif" SIZE="18"/>
<node CREATED="1419841636645" ID="ID_111055079" MODIFIED="1419841651080" POSITION="right" TEXT="Spielaufbau">
<node CREATED="1419841653553" ID="ID_1835062717" MODIFIED="1419841669345" TEXT="&#xe4;hnlich wie Pong"/>
<node CREATED="1419841671210" ID="ID_581437102" MODIFIED="1419841680858" TEXT="links megaman, der periodisch nach rechts schie&#xdf;t"/>
<node CREATED="1419841682790" ID="ID_1518133891" MODIFIED="1419841692064" TEXT="rechts protoman, der versucht die kugeln von megaman abzuwehren"/>
<node CREATED="1419841693908" ID="ID_137221037" MODIFIED="1419841705345" TEXT="rechts hinter protoman sind metools, die er versucht zu besch&#xfc;tzen"/>
<node CREATED="1419841709775" ID="ID_1271789723" MODIFIED="1420328980237" TEXT="am oberen rand sind zwei scheinwerfer, die das spielfeld bestrahlten (als Erweiterung geplant, wenn Physicengine integriert)">
<node CREATED="1419841731314" ID="ID_1694806503" MODIFIED="1419841745264" TEXT="bei h&#xf6;he 0 und weite 1/4 bildschirmbreite"/>
<node CREATED="1419841731314" ID="ID_1589635990" MODIFIED="1419841750642" TEXT="bei h&#xf6;he 0 und weite 3/4 bildschirmbreite"/>
</node>
</node>
<node CREATED="1419841622626" ID="ID_667727622" MODIFIED="1419841627774" POSITION="right" TEXT="Spielablauf">
<node CREATED="1419841772321" ID="ID_977350203" MODIFIED="1419841775874" TEXT="AI">
<node CREATED="1419841784714" ID="ID_478766203" MODIFIED="1419841799157" TEXT="bewegt Megaman periodisch an eine zuf&#xe4;llige h&#xf6;he am linken bildschirm rand"/>
<node CREATED="1419841802637" ID="ID_161338932" MODIFIED="1419841817609" TEXT="nach der verschiebung schie&#xdf;t megaman nach rechts"/>
</node>
<node CREATED="1419841777913" ID="ID_1418143755" MODIFIED="1419841781770" TEXT="Spieler">
<node CREATED="1419841826776" ID="ID_1431455101" MODIFIED="1419841837361" TEXT="kann protoman mit pfeiltaste rauf/runter nach oben/unten bewegen"/>
<node CREATED="1419841839758" ID="ID_1289696970" MODIFIED="1419841848760" TEXT="muss sch&#xfc;sse von megaman mit dem schild abwehren"/>
</node>
<node CREATED="1419841859491" ID="ID_160695277" MODIFIED="1419841862613" TEXT="Regeln">
<node CREATED="1419841863743" ID="ID_282132458" MODIFIED="1419841880861" TEXT="wird ein schuss von protoman nicht geblocked, wird ein metool get&#xf6;tet"/>
<node CREATED="1419841883570" ID="ID_1543932439" MODIFIED="1419841890783" TEXT="sind keine metools mehr &#xfc;brig ist das spiel zu ende"/>
<node CREATED="1419841908244" ID="ID_1935045385" MODIFIED="1419841961430" TEXT="&#xfc;berlebt der spieler eine gewisse zeit, sodass noch metools &#xfc;brig sind, hat der spieler gewonnen und bekommt punkte f&#xfc;r jeden &#xfc;berlebenden metool"/>
</node>
</node>
<node CREATED="1419841643355" ID="ID_1290534474" MODIFIED="1419841647768" POSITION="right" TEXT="Rollen">
<node CREATED="1419841966771" ID="ID_787668255" MODIFIED="1419841968599" TEXT="Megaman">
<node CREATED="1419841971240" ID="ID_1252077837" MODIFIED="1419842024032" TEXT="muss unbedingt alle metools t&#xf6;ten"/>
</node>
<node CREATED="1419841997158" ID="ID_1053668647" MODIFIED="1419841999071" TEXT="Protoman">
<node CREATED="1419842000567" ID="ID_395858005" MODIFIED="1419842005776" TEXT="Besch&#xfc;tzer der metools"/>
</node>
<node CREATED="1419842370952" ID="ID_137888742" MODIFIED="1419842374322" TEXT="Bosse aus MM3">
<node CREATED="1419842375671" ID="ID_580267073" MODIFIED="1419842389456" TEXT="wird ein Schuss des Bosses gefangen, so &#xe4;ndert sich die Hintergrundmusik"/>
</node>
<node CREATED="1419842007781" ID="ID_794673984" MODIFIED="1419842009933" TEXT="metools">
<node CREATED="1419842011225" ID="ID_792054524" MODIFIED="1419842022228" TEXT="arme kleine Roboter, die es zu retten gilt"/>
</node>
</node>
<node COLOR="#ff0033" CREATED="1419842034550" ID="ID_1442541230" MODIFIED="1420040330235" POSITION="left" STYLE="fork" TEXT="ToDo">
<edge COLOR="#808080" STYLE="bezier" WIDTH="thin"/>
<font BOLD="true" NAME="SansSerif" SIZE="14"/>
<node CREATED="1420328669551" ID="ID_663596241" MODIFIED="1420328671951" TEXT="refactoring">
<node CREATED="1420328673337" ID="ID_394439686" MODIFIED="1420328677987" TEXT="com.megaman.core">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420328692713" ID="ID_801467169" MODIFIED="1420328698460" TEXT="com.megaman.core.graphics"/>
<node CREATED="1420328699974" ID="ID_1386167804" MODIFIED="1420328705577" TEXT="com.megaman.core.model"/>
<node CREATED="1420328707163" ID="ID_1892478579" MODIFIED="1420328711597" TEXT="com.megaman.core.utils"/>
<node CREATED="1420328716032" ID="ID_1166159853" MODIFIED="1420328719779" TEXT="come.megaman.core.enums"/>
<node CREATED="1420328734430" HGAP="21" ID="ID_592631445" MODIFIED="1420328748205" TEXT="com.egaman.enums" VSHIFT="46"/>
<node CREATED="1420328750101" ID="ID_1659635191" MODIFIED="1420328761410" TEXT="com.egaman.model"/>
<node CREATED="1420328762915" ID="ID_1824259414" MODIFIED="1420328766501" TEXT="com.egaman.ai.states"/>
<node CREATED="1420328783308" HGAP="21" ID="ID_1187131992" MODIFIED="1420328796308" TEXT="com.megaman.gamestates." VSHIFT="30"/>
<node CREATED="1420328789462" ID="ID_89221600" MODIFIED="1420328793859" TEXT="com.egaman.gamestates.logic"/>
<node CREATED="1420328799163" HGAP="18" ID="ID_1227875420" MODIFIED="1420328812098" TEXT="com.egaman.menu" VSHIFT="29"/>
<node CREATED="1420328803684" ID="ID_1099668751" MODIFIED="1420328807575" TEXT="com.megaman.menu.pages"/>
</node>
<node CREATED="1420020955608" ID="ID_991042120" MODIFIED="1420151425262" TEXT="internationalisierung ">
<node CREATED="1420020962076" ID="ID_1837786978" MODIFIED="1420020972811" TEXT="name attribute f&#xfc;r gameobjects benutzen"/>
<node CREATED="1420130542788" ID="ID_951857993" MODIFIED="1420130545688" TEXT="und men&#xfc;punkte"/>
</node>
<node CREATED="1419842424700" ID="ID_1082518376" MODIFIED="1419842440445" TEXT="Particlesystem hinzuf&#xfc;gen f&#xfc;r einen coolen Effekt, wenn Protoman nach oben/unten &quot;fliegt&quot;"/>
<node CREATED="1419935428914" ID="ID_1190959516" MODIFIED="1419935568614" TEXT="Finalisierungen">
<node CREATED="1419935444630" ID="ID_1866879479" MODIFIED="1420019687538" TEXT="protoman weiter links platzieren, damit metools dahinter platz haben">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419935456541" ID="ID_1524849556" MODIFIED="1419935468246" TEXT="hintergrund mit Tiled basteln f&#xfc;r 800x600 aufl&#xf6;sung"/>
<node CREATED="1419935503819" FOLDED="true" ID="ID_1496544748" MODIFIED="1420223316758" TEXT="metools als statemachine integrieren">
<icon BUILTIN="button_ok"/>
<node CREATED="1419935513053" ID="ID_320241651" MODIFIED="1419935514316" TEXT="IDLE">
<node CREATED="1419935516087" ID="ID_654793222" MODIFIED="1419935528447" TEXT="schaut zuf&#xe4;llig aus seinem helm hervor alle X sekunden"/>
</node>
<node CREATED="1419935531158" ID="ID_165128738" MODIFIED="1419935539601" TEXT="FLEE">
<node CREATED="1419935540625" ID="ID_1821785686" MODIFIED="1419935545893" TEXT="wenn metool getroffen wurde"/>
<node CREATED="1419935547619" ID="ID_1674129534" MODIFIED="1419935554378" TEXT="flieh aus dem rechten bildschirmrand"/>
</node>
</node>
</node>
<node CREATED="1419889430598" ID="ID_389229075" MODIFIED="1419935314685" TEXT="code kommentieren!!!">
<node CREATED="1420328588883" ID="ID_1391178741" MODIFIED="1420328596731" TEXT="com.megaman.core">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420328604354" ID="ID_1184251841" MODIFIED="1420328612412" TEXT="com.megaman.core.enums"/>
<node CREATED="1420328614544" ID="ID_1126326310" MODIFIED="1420328620527" TEXT="com.egaman.core.graphics"/>
<node CREATED="1420328622146" ID="ID_513759877" MODIFIED="1420328627074" TEXT="com.megaman.core.model"/>
<node CREATED="1420328628555" ID="ID_212061464" MODIFIED="1420328632023" TEXT="com.egaman.core.utils"/>
</node>
<node BACKGROUND_COLOR="#33cc00" COLOR="#000000" CREATED="1419842458351" FOLDED="true" ID="ID_1988558492" MODIFIED="1420328948403" STYLE="fork" TEXT="Erweiterungen">
<edge COLOR="#808080" STYLE="bezier" WIDTH="thin"/>
<node CREATED="1419842461614" ID="ID_63926541" MODIFIED="1419842574874" TEXT="auf Physicengine umstellen (Box2D)"/>
<node CREATED="1419842471751" ID="ID_1589155767" MODIFIED="1419842574874" TEXT="In der Mitte oben des Spielfeldes fallen &quot;M&#xfc;llbl&#xf6;cke&quot; aus MM3 periodisch herunter"/>
<node CREATED="1419842498315" ID="ID_1611549961" MODIFIED="1419842574874" TEXT="In der Mitte am Boden ist eine kleine Plattform von der die M&#xfc;llbl&#xf6;cke abprallen"/>
<node CREATED="1419842534513" ID="ID_815374415" MODIFIED="1419842574874" TEXT="trifft ein Schuss von megaman einen m&#xfc;llblock so wird dieser in mehrere kleine m&#xfc;llbl&#xf6;cke aufgeteilt die richtung protoman fliegen"/>
<node CREATED="1419842623888" ID="ID_1826108349" MODIFIED="1419842637366" TEXT="protoman wird leicht zur&#xfc;ckgedr&#xfc;ckt, wenn er einen schuss abf&#xe4;ngt">
<node CREATED="1419842638689" ID="ID_1090523593" MODIFIED="1419842646714" TEXT="er geht dann zur ausgangsposition langsam retour"/>
</node>
<node CREATED="1419935606590" ID="ID_1364228087" MODIFIED="1419935624621" TEXT="protoman muss erst beschleunigen beim rauf/runterfliegen bevor er endgeschwindigkeit erreicht"/>
<node CREATED="1419842447808" ID="ID_1610726709" MODIFIED="1419842455139" TEXT="Light in Szene hinzuf&#xfc;gen"/>
</node>
</node>
<node BACKGROUND_COLOR="#00ccff" CREATED="1420328514925" FOLDED="true" HGAP="15" ID="ID_892494079" MODIFIED="1420328569336" POSITION="left" STYLE="fork" TEXT="Erledigte Aufgaben" VSHIFT="44">
<edge COLOR="#808080" STYLE="bezier" WIDTH="thin"/>
<font BOLD="true" NAME="SansSerif" SIZE="14"/>
<node CREATED="1420040332238" FOLDED="true" ID="ID_520736744" MODIFIED="1420134127383" TEXT="renderoptimierung">
<icon BUILTIN="button_ok"/>
<node CREATED="1420040335078" ID="ID_536267613" MODIFIED="1420040339238" TEXT="zuerst nur characters rendern"/>
<node CREATED="1420040340275" ID="ID_1557730314" MODIFIED="1420040342127" TEXT="dann missiles"/>
<node CREATED="1420040343222" ID="ID_1995608816" MODIFIED="1420040350583" TEXT="--&gt; weniger texturebinding"/>
</node>
<node CREATED="1419889401921" FOLDED="true" ID="ID_923178520" MODIFIED="1420130526403" TEXT="in git repository einrichten">
<icon BUILTIN="button_ok"/>
<node CREATED="1419889410407" ID="ID_1145555665" MODIFIED="1419889414935" TEXT="auf dropbox speichern"/>
</node>
<node CREATED="1419842239993" FOLDED="true" ID="ID_1593774829" MODIFIED="1419889226862" TEXT="AI f&#xfc;r Megaman hinzuf&#xfc;gen">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842248125" ID="ID_1230938717" MODIFIED="1419842262745" TEXT="alle X Sekunden geht er vom IDLE zustand in einen neuen &#xfc;ber"/>
<node CREATED="1419842265117" ID="ID_842429938" MODIFIED="1419842266829" TEXT="Zust&#xe4;nde">
<node CREATED="1419842267388" FOLDED="true" ID="ID_1458942693" MODIFIED="1419880996297" TEXT="IDLE">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842270514" ID="ID_788094525" MODIFIED="1419842281566" TEXT="megaman steht nur still"/>
</node>
<node CREATED="1419842284323" FOLDED="true" ID="ID_179197231" MODIFIED="1419880996297" TEXT="ATTACK">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842290829" ID="ID_720776568" MODIFIED="1419842307543" TEXT="megaman wechselt zu einer zuf&#xe4;lligen position (fade out &amp; fade in) und schie&#xdf;t einmal"/>
</node>
<node CREATED="1419842322949" FOLDED="true" ID="ID_689307503" MODIFIED="1419889212412" TEXT="CALL_BOSS">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842334577" ID="ID_50147141" MODIFIED="1419842345678" TEXT="megaman ruft um hilfe von einem zuf&#xe4;lligen boss aus MM3"/>
<node CREATED="1419842347159" ID="ID_839039105" MODIFIED="1419842365906" TEXT="der Boss spawned an einer zuf&#xe4;lligen Stellt auf megaman&apos;s seite und schie&#xdf;t einmal"/>
</node>
</node>
</node>
<node CREATED="1419842118819" FOLDED="true" ID="ID_913698694" MODIFIED="1419889226861" TEXT="Optimierungen">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842085210" ID="ID_1956158696" MODIFIED="1419865435954" TEXT="Die Klasse Pool f&#xfc;r Megman&apos;s Sch&#xfc;sse hernehmen">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419842201722" ID="ID_153028881" MODIFIED="1419868862366" TEXT="TexturePacker f&#xfc;r TextureAtlas zu Beginn des Spiels">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419842218331" ID="ID_1053702094" MODIFIED="1419880956911" TEXT="Sprites nicht rendern, die au&#xdf;erhalb des Bildschirms gelangen">
<icon BUILTIN="button_ok"/>
</node>
</node>
<node CREATED="1419842064687" FOLDED="true" ID="ID_1399034489" MODIFIED="1419889265691" TEXT="Mit LibGDX vertraut machen">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842071768" ID="ID_993939883" MODIFIED="1419842082244" TEXT="&quot;A simple game&quot; vom wiki als Grundbaustein des Spiels hernehmen"/>
</node>
<node CREATED="1419842408667" FOLDED="true" ID="ID_458939699" MODIFIED="1419935307949" TEXT="Steuerung einbauen f&#xfc;r Tastatur und Gamepad">
<icon BUILTIN="button_ok"/>
<node CREATED="1419889276939" ID="ID_1588034771" MODIFIED="1419889282676" TEXT="protoman rauf/runter bewegen"/>
</node>
<node CREATED="1419842154520" FOLDED="true" ID="ID_1322008454" MODIFIED="1419935326096" TEXT="mit libgdx ai vertraut machen (Stichwort: statemachines)">
<icon BUILTIN="button_ok"/>
<node CREATED="1419842177978" ID="ID_1653915951" MODIFIED="1419880984944" TEXT="Beispiel von wiki anschauen mit Bob dem Goldabbauer">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419880976654" ID="ID_1723204180" MODIFIED="1419880980146" TEXT="behavior tree????"/>
</node>
<node CREATED="1419886763788" FOLDED="true" ID="ID_879049194" MODIFIED="1419940347415" TEXT="make pool for bosses">
<icon BUILTIN="button_ok"/>
<node CREATED="1419889364230" ID="ID_188694373" MODIFIED="1419938252520" TEXT="restlichen bosse einbauen">
<icon BUILTIN="button_ok"/>
<node CREATED="1419889369295" ID="ID_1262964036" MODIFIED="1419937393642" TEXT="snakeman">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889372845" ID="ID_1791942428" MODIFIED="1419937692810" TEXT="needleman">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889379669" ID="ID_850257894" MODIFIED="1419937743933" TEXT="hardman">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889384679" ID="ID_286599274" MODIFIED="1419937894042" TEXT="topman">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889387536" ID="ID_1487828695" MODIFIED="1419938093036" TEXT="geminiman">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889390485" ID="ID_306324789" MODIFIED="1419938172493" TEXT="magnetman">
<icon BUILTIN="button_ok"/>
</node>
</node>
</node>
<node CREATED="1420019701788" FOLDED="true" HGAP="32" ID="ID_1540294545" MODIFIED="1420137515611" TEXT="Refactoring">
<icon BUILTIN="button_ok"/>
<node CREATED="1420019752623" FOLDED="true" ID="ID_1312177603" MODIFIED="1420025009808" TEXT="resourcemanager">
<icon BUILTIN="button_ok"/>
<node CREATED="1420019734335" ID="ID_659190931" MODIFIED="1420023863588" TEXT="Resourcemanager als singleton (enum)">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420019770538" FOLDED="true" ID="ID_1379534605" MODIFIED="1420023867355" TEXT="nicht alle sounds sofort laden">
<icon BUILTIN="button_ok"/>
<node CREATED="1420019789879" ID="ID_268647378" MODIFIED="1420019794310" TEXT="sollten onDemand geladen werden"/>
</node>
<node CREATED="1420019796901" ID="ID_1712733464" MODIFIED="1420025004708" TEXT="dispose Methoden f&#xfc;r sound/music/sprite">
<icon BUILTIN="button_ok"/>
</node>
</node>
<node CREATED="1420020116884" FOLDED="true" ID="ID_970395408" MODIFIED="1420025282302" TEXT="GameUtils">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020122132" ID="ID_1716873934" MODIFIED="1420025183191" TEXT="playSound methode dorthin verschieben">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420020136178" ID="ID_1719135343" MODIFIED="1420025183190" TEXT="playmusic methode dorthin verschieben">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420020903996" ID="ID_585669189" MODIFIED="1420025278144" TEXT="intersects methode f&#xfc;r zwei gameobj">
<icon BUILTIN="button_ok"/>
</node>
</node>
<node CREATED="1420020357026" FOLDED="true" ID="ID_1290012856" MODIFIED="1420025360440" TEXT="neue Klasse GameInputAdapter (benutze bestehende GameInput klasse)">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020364976" ID="ID_1960120917" MODIFIED="1420020373738" TEXT="extends InputAdapter implements ControllerListener"/>
</node>
<node CREATED="1420020498336" FOLDED="true" ID="ID_333810162" MODIFIED="1420025540414" TEXT="MegamanGoneCrazy">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020504413" ID="ID_1610035751" MODIFIED="1420020517704" TEXT="neue methode setGameInput(GameInputAdapter)"/>
</node>
<node CREATED="1420019972891" FOLDED="true" ID="ID_749530039" MODIFIED="1420030003461" TEXT="GSGameLogic.java">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020011896" ID="ID_110080599" MODIFIED="1420029997429" TEXT="helper klasse f&#xfc;r die logik des gamestates">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420020026394" ID="ID_832748054" MODIFIED="1420025902853" TEXT="beinhaltet initialize/update/render/dispose methode">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420020325061" ID="ID_1762004110" MODIFIED="1420030000298" TEXT="beinhaltet key/mouse events">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420020338220" ID="ID_78452531" MODIFIED="1420025895069" TEXT="erbt von neuer klasse GameInputAdapter">
<icon BUILTIN="button_ok"/>
</node>
</node>
<node CREATED="1420020538740" FOLDED="true" ID="ID_688910274" MODIFIED="1420030012604" TEXT="GSGame">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020544226" ID="ID_577342226" MODIFIED="1420020559240" TEXT="bei create -&gt; rufe setGameInput(GameInputAdapter) auf"/>
</node>
<node CREATED="1420020181875" FOLDED="true" ID="ID_444222131" MODIFIED="1420030062371" TEXT="Megaman &amp; Boss &amp; GameInput">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020208558" ID="ID_645970653" MODIFIED="1420020214197" TEXT="GSGame attribute entfernen"/>
</node>
<node CREATED="1420020408459" FOLDED="true" ID="ID_1239592475" MODIFIED="1420026240007" TEXT="GameState">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020469244" ID="ID_736579625" MODIFIED="1420020474884" TEXT="remove inputProcessor"/>
<node CREATED="1420020475954" ID="ID_153033924" MODIFIED="1420020479189" TEXT="remove resourceManager"/>
</node>
<node CREATED="1420020814703" FOLDED="true" ID="ID_62345930" MODIFIED="1420030643152" TEXT="GameObject">
<icon BUILTIN="button_ok"/>
<node CREATED="1420020821466" ID="ID_104271005" MODIFIED="1420020856612" TEXT="jedem gameobject wird eine referenz von GSGameLogic &#xfc;bergeben um an interne logik dinge ranzukommen (spawnMissile, etc.)"/>
</node>
<node CREATED="1420028216135" ID="ID_1855522531" MODIFIED="1420031654189" TEXT="megamangonecrazy klasse auf etwas allgemeines umbenennen">
<icon BUILTIN="button_ok"/>
</node>
</node>
<node CREATED="1420130548538" FOLDED="true" HGAP="25" ID="ID_1563508840" MODIFIED="1420137508067" TEXT="GameMenu" VSHIFT="-18">
<icon BUILTIN="button_ok"/>
<node CREATED="1420130619183" ID="ID_148621613" MODIFIED="1420130622478" TEXT="MenuPage">
<node CREATED="1420130557851" ID="ID_1848277616" MODIFIED="1420130566524" TEXT="attribute">
<node CREATED="1420130567065" ID="ID_1866591499" MODIFIED="1420130567887" TEXT="table"/>
<node CREATED="1420130631984" ID="ID_1235414802" MODIFIED="1420130636214" TEXT="label[] options"/>
</node>
<node CREATED="1420130654729" ID="ID_35743981" MODIFIED="1420130905682" TEXT="methoden">
<node CREATED="1420130658739" ID="ID_1914031323" MODIFIED="1420130722259" TEXT="getInitialOption"/>
<node CREATED="1420130683216" ID="ID_108760482" MODIFIED="1420130716998" TEXT="getOptions"/>
<node CREATED="1420130907551" ID="ID_32941337" MODIFIED="1420130970232" TEXT="processSelection(int index)"/>
</node>
</node>
</node>
<node CREATED="1420151385913" ID="ID_85691639" MODIFIED="1420194099819" TEXT="konfigurationen vom hauptmen&#xfc; in cfg datei abspeichern und entsprechend laden bei spielstart">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1420130573814" FOLDED="true" ID="ID_290885679" MODIFIED="1420195171994" TEXT="fade zu animatedgameobj hinzuf&#xfc;gen">
<icon BUILTIN="button_ok"/>
<node CREATED="1420130582174" ID="ID_699438795" MODIFIED="1420130600770" TEXT="methode fade(targettransparency,time)"/>
</node>
<node CREATED="1420191499953" FOLDED="true" ID="ID_1838735578" MODIFIED="1420197004522" TEXT="spielelogik um&#xe4;ndern">
<icon BUILTIN="button_ok"/>
<node CREATED="1420191510398" ID="ID_19310888" MODIFIED="1420191519255" TEXT="megaman schie&#xdf;t zu beginn 5x"/>
<node CREATED="1420191521200" ID="ID_1170755219" MODIFIED="1420191530144" TEXT="dann wechselt er in zustand call boss">
<node CREATED="1420191531331" ID="ID_810287915" MODIFIED="1420191657899" TEXT="dort wird alle 3 runden ein boss zus&#xe4;tzlich gespawned"/>
<node CREATED="1420191603822" ID="ID_1644524510" MODIFIED="1420191680903" TEXT="wenn boss wechselt -&gt; &#xe4;ndere musik auf bossmusik"/>
<node CREATED="1420192023669" ID="ID_1353227832" MODIFIED="1420192034543" TEXT="boss wechselt nach 9 sch&#xfc;ssen"/>
</node>
<node CREATED="1420191687893" ID="ID_1096057471" MODIFIED="1420191699387" TEXT="megaman wird alle 5 sch&#xfc;sse schneller">
<node CREATED="1420191702959" ID="ID_36852352" MODIFIED="1420192012522" TEXT="start bei 1.95 sekunden"/>
<node CREATED="1420191823940" ID="ID_1902458511" MODIFIED="1420191871004" TEXT="pro intervall -0.1 sekunden"/>
<node CREATED="1420191872870" ID="ID_1337727024" MODIFIED="1420191951188" TEXT="megaman verschie&#xdf;t insgesamt 60 sch&#xfc;sse"/>
</node>
</node>
<node CREATED="1419842129212" FOLDED="true" ID="ID_869470759" MODIFIED="1420209299508" TEXT="F&#xfc;nf GameStates einf&#xfc;gen">
<icon BUILTIN="button_ok"/>
<node CREATED="1419889331525" ID="ID_1430151275" MODIFIED="1420209293845" TEXT="Game Over">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889335362" ID="ID_685054708" MODIFIED="1420209293846" TEXT="Highscore">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419842145730" FOLDED="true" ID="ID_1413027856" MODIFIED="1420151364001" TEXT="Hauptmen&#xfc;">
<icon BUILTIN="button_ok"/>
<node CREATED="1419935348595" ID="ID_1024485679" MODIFIED="1419935352540" TEXT="resume game"/>
<node CREATED="1419935353848" ID="ID_1234271383" MODIFIED="1419935358005" TEXT="new game"/>
<node CREATED="1419935359582" ID="ID_1080852838" MODIFIED="1419935362366" TEXT="settings">
<node CREATED="1419935471900" ID="ID_1068332442" MODIFIED="1419935473771" TEXT="video">
<node CREATED="1419935475100" ID="ID_1310101357" MODIFIED="1419935480315" TEXT="fullscreen y/n"/>
<node CREATED="1419935484060" ID="ID_491648371" MODIFIED="1419935488295" TEXT="windowsize"/>
</node>
<node CREATED="1419935368731" ID="ID_1211149118" MODIFIED="1419935372325" TEXT="controller settings">
<node CREATED="1419935373420" ID="ID_357430666" MODIFIED="1419935376796" TEXT="tastatur + controller"/>
</node>
<node CREATED="1419935380186" ID="ID_286785633" MODIFIED="1419935382244" TEXT="audio settings">
<node CREATED="1419935383549" ID="ID_1033422370" MODIFIED="1419935386300" TEXT="volume music"/>
<node CREATED="1419935387430" ID="ID_1320736589" MODIFIED="1419935389854" TEXT="volume sound"/>
<node CREATED="1419935393981" ID="ID_1620540920" MODIFIED="1419935404064" TEXT="juke box f&#xfc;r alle musiktitel"/>
</node>
</node>
<node CREATED="1419935363794" ID="ID_624567701" MODIFIED="1419935365407" TEXT="quit game"/>
<node CREATED="1420032263879" ID="ID_222502451" MODIFIED="1420032268605" TEXT="scene 2d libgdx wiki"/>
</node>
<node CREATED="1419842149276" ID="ID_678395994" MODIFIED="1420137527931" TEXT="Spiel">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889326868" ID="ID_1702368906" MODIFIED="1420191466199" TEXT="Pause">
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1419889246751" ID="ID_1144299163" MODIFIED="1420032250998" TEXT="tutorial im wiki anschauen f&#xfc;r erweiterungen von &quot;a simple game&quot;">
<icon BUILTIN="button_ok"/>
</node>
</node>
</node>
</node>
</map>
