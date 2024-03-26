package jp.co.tellme.marvellous.taskmaster.repository

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DbUnitConfiguration
import jp.co.tellme.marvellous.taskmaster.config.DbTestConfig
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

// エンティティとリポジトリのテストを行うためのAuto Configurationを使用するためのアノテーション
// つまりはdbテストを簡単かつ効率的にするためのもの。
@DataJpaTest
// @DataJpaTestなどのdbテストを自動設定してくれるアノテーションは、勝手に組み込みdb(インメモリdb)を
// テスト用dbに置き換えようとしてくる。 それを制御して設定したDataSource(DB)を使うようにしている。
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// テストメソッド(@Test)の前後にカスタムコード(ログ等)を挿入したり、テスト実行中の特定のイベントを検出したりする設定
@TestExecutionListeners(
    // listeners: testContextManagerに登録するTestExecutionListenersの設定。
    // リスナーは、DBへの操作に関するイベントを監視し、結果や状況に応じてアクションするコンポーネント。データベースリスナーとも呼ばれる。
    // DBUnitTestExecutionListenersはdbunitを利用する際に必須となるリスナー
    // テスト実行時にspringのDIを利用できるようにする。
    listeners = [DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class],
    // リスナーを継承しないクラスでリスナーが呼ばれた時(dbテスト外?)、ここで設定したリスナーも追加するという記述。
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
// dbunitのテスト用dbを設定する。databaseConnectionは指定したBeanのdb設定を利用する。
@DbUnitConfiguration(databaseConnection = ["dbUnitDatabaseConnection"])
// クラス内で使用するコンフィグファイルを指定する。
@Import(DbTestConfig::class)
// @DataJpaTestはテストごとに都度ロールバックするが、これによって設定が上書きされ、
// 都度ロールバックしないようになっている
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// 使用するプロファイル(yml)の指定。application-"この部分".yml
@ActiveProfiles("test")
abstract class RepositoryTestBase {
    companion object {
        // テストデータ置き場のパス,resources以下
        const val baseTestDataFilePath = "/dbunit/"
    }
}
