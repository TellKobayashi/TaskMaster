package jp.co.tellme.marvellous.taskmaster.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

// @Configuration: このファイルがconfigクラスだよって認識させる。
@Configuration
// spring securityが有効になり、クラス内で設定が可能に
@EnableWebSecurity
class SecurityConfigurer {
    // SecurityFilterChain: SecurityFilterProxyが受け取ったリクエストに対して、適用するフィルターのリストを管理するためのインターフェース。
    // SecurityFilterProxy: アクセスのエントリポイントとなるサーブレットフィルタクラス。
    @Bean
    protected fun filterHttp(http: HttpSecurity): SecurityFilterChain {
        http
            // 認証の要求の有無をリクエストのパターンによって行う設定。今回は全てのリクエストに認証をしないようにしている。
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
            // Cross Site Request Forgery: 利用者がとあるサイトにログインした状態で、悪意のある別のサイトにアクセスする。
            // その状態だと、悪意のあるサイト経由で攻撃者がログインが必要なリクエスト(購入、送金、退会...)を送ることができる、サイトを跨いでリクエストを送る攻撃。
            .csrf {csrf ->
                // csrfトークンを持っていない状態の/user/createはcsrf対策から除外する。
                csrf.ignoringRequestMatchers("/api/v1/user/create")
            }
        return http.build()
    }
}