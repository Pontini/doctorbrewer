package pontini.systems

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxTestSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {

//                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
//                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
//                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
//                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                val testScheduller = TestScheduler()
                RxJavaPlugins.setIoSchedulerHandler { testScheduller }
                RxJavaPlugins.setComputationSchedulerHandler { testScheduller}
                RxJavaPlugins.setNewThreadSchedulerHandler { testScheduller }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduller }


                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
