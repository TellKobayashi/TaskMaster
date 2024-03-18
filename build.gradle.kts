import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// gradleのbuildに必要な依存関係
plugins {
	// applicationプラグインは不要と判断。 --> applicationはmain関数を利用したJVMアプリケーションの開発を支援するもののため。
	// 使い方はわからないが、一旦ideaプラグインは導入。
	idea
	// initializrから導入した際に自動で入っていたもの。 >>
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	// O/RマッパーはJPAだけでやってみる。mybatisは使わない。
	kotlin("plugin.jpa") version "1.9.22"
	// << ここまで
	// 個人の判断で導入したplugins 3/19最新ver
	// gitのタグでバージョン管理するプラグインの中ではアンパイかなと。長期間アップデートが入っていないのは心配。
	id("com.palantir.git-version") version "3.0.0"
	// ローカルでapiを叩く用のDBマイグレーション用
	id("org.flywaydb.flyway") version "9.4.0"
}

// flywayの設定に必要。これがないとflywayがdbに接続できない。
// https://qiita.com/wb773/items/ca85cf05c90c3037ed25
buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.flywaydb:flyway-mysql:8.3.0")
		classpath("mysql:mysql-connector-java:8.0.32")
	}
}

// gradleの設定わからんすぎて禿げそう。実装に入る前の環境設定が鬼難易度で苦しい。

group = "jp.co.tellme.marvellous"
version = "1.0.0"
// git-describeがないとbuild時エラーになる。
// version = gitVersion()

// ライブラリを持ってくるリポジトリ。今回はmavenCentralからだけで済む。
repositories {
	mavenCentral()
}

// 動的にバージョンを選択するだのっぽいが、よくわからんのでノータッチ。
java {
	sourceCompatibility = JavaVersion.VERSION_17
}

// dependency-managementプラグインの機能。対応したバージョンのプラグインを入れてくれる。
// BOMという機能らしいが、railsでは標準機能だったような気がする。クソ便利。
dependencyManagement {
	imports {
		mavenBom("org.junit:junit-bom:5.4.2")
	}
}

// プロジェクトの記述に必要なライブラリの依存関係
dependencies {
	// 元から入ってたやつ。
	// O/Rマッパー
	// application.propertiesにdbの接続情報を書く必要があることに注意
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// ログイン機能のためにSpring security のOauth2.0を使う。
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// RESTfulAPIだからjacksonは必要...だと思う。
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	// なんかkotlinにはリフレクト機能があるとかなんとか勉強した気がする...
	// インスタンスを生成しなくてもクラスの実装をコードが参照できるとかなんとか...だったような
	// ExceptionHandler(<クラス名>::class)でKClassをハンドリングするんだったっけ？
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test"){
		// 単体でjunitを導入するので、競合しないようにこっちのjunitはexcludeする。
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.security:spring-security-test")

	// 個人の判断で入れたやつ。
	// mockito。モック。最新の5.3.0はnotfoundとなったので古いものを入れている。
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	// こいつはpluginsで導入済みなので入れなくていい。
	// implementation("org.springframework.boot:spring-boot-gradle-plugin:3.2.3")
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}



tasks.withType<KotlinCompile> {
	kotlinOptions {
		// jsr 305は確かnotnullとかのアノテーションを追加してるやつだったはず。
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// flywayが接続するためのDB情報
// DDLはresources/db/migration/配下に書く。
flyway {
	url = "jdbc:mysql://127.0.0.1:13306/taskmaster_db"
	user = "taskmaster"
	password = "taskmaster"
}