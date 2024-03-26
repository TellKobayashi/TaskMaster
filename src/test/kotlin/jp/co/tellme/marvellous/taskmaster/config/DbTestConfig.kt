package jp.co.tellme.marvellous.taskmaster.config

import com.github.springtestdbunit.bean.DatabaseConfigBean
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.dbunit.ext.mysql.MySqlDataTypeFactory
import org.dbunit.ext.mysql.MySqlMetadataHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@TestConfiguration
class DbTestConfig {
    @Bean(name = ["dataSourceProperties"])
    // ~.ymlなどの設定ファイルから、"spring.datasource"で始まるものを取ってきている
    @ConfigurationProperties(prefix = "spring.datasource")
    // datasourcePropertiesメソッドはDataSourcePropertiesをシングルトンにしDIコンテナに登録することが目的なので、
    // 直接使用はしない。
    // @Autowiredや@Qualifierで暗黙的に使用されている。
    fun dataSourceProperties(): DataSourceProperties {
        // 引数には書いていないが、@ConfigurationPropertiesから取ってきた値がコンストラクタに入り、
        // DataSourcePropertiesのインスタンスが生成されている。
        return DataSourceProperties()
    }

    // dbテストのデータソース
    @Bean(name = ["dataSource"])
    fun dataSource(@Qualifier("dataSourceProperties") properties: DataSourceProperties): DataSource {
        // hikariデータ接続プールを構成している。
        // プールはDBの接続状況をクローズせずに再度利用するものだった気がする。そのほかには、今すぐには使わないが使う予定のある値などを貯めておくもの。
        return properties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    // dbunitのコンフィグ
    @Bean(name = ["dbUnitDatabaseConfig"])
    fun dbUnitDatabaseConfig(): DatabaseConfigBean = DatabaseConfigBean().apply {
        allowEmptyFields = true
        datatypeFactory = MySqlDataTypeFactory()
        metadataHandler = MySqlMetadataHandler()
    }

    // dbunitのデータソース設定
    @Bean(name = ["dbUnitDatabaseConnection"])
    fun dbUnitDatabaseConnection(
        @Qualifier("dbUnitDatabaseConfig") dbUnitDatabaseConfig: DatabaseConfigBean,
        @Qualifier("dataSource") dataSource: DataSource
    ): DatabaseDataSourceConnectionFactoryBean = DatabaseDataSourceConnectionFactoryBean(dataSource).apply {
        setDatabaseConfig(dbUnitDatabaseConfig)
    }
}