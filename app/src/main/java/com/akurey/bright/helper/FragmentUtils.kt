//package com.akurey.bright.helper
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import com.akurey.bright.R
//
//class FragmentUtils {
//    fun addFragment(supportFragmentManager: FragmentManager, fragment: Fragment, isFirstFragment: Boolean, isRecoveredFragment: Boolean = false) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//        if (isRecoveredFragment) {
//            transaction.replace(R.id.container, fragment)
//        } else {
//            transaction.add(R.id.container, fragment)
//            if (!isFirstFragment) {
//                transaction.addToBackStack(fragment::class.java.name)
//            }
//        }
//        transaction.commit()
//    }
//    fun replaceFragment(supportFragmentManager: FragmentManager, fragment: BaseFragment, isFirstFragment: Boolean, isRecoveredFragment: Boolean =
//        false) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//        if (isRecoveredFragment) {
//            transaction.replace(R.id.container, fragment)
//        } else {
//            transaction.replace(R.id.container, fragment)
//            if (!isFirstFragment) {
//                transaction.addToBackStack(fragment::class.java.name)
//            }
//        }
//        transaction.commit()
//    }
//}

