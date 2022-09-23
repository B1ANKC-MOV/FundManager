import Vue from 'vue'
import Router from 'vue-router'

import Login from './components/Login'
import TransactionRecord from './components/TransactionRecord'
import Register from './components/Register'
import FundMarket from './components/FundMarket'
import SelfInfo from './components/SelfInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Login',
      name: 'Login',
      component: Login,
      meta: {
        doNotShowFrame: true
      }
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register,
      meta: {
        doNotShowFrame: true
      }
    },
    {
      path: '/PosInfo',
      name: 'PosInfo',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'PosInfo'
      }
    },
    {
      path: '/TransactionRecord',
      name: 'TransactionRecord',
      component: TransactionRecord,
    },
    {
      path: '/LatestFund',
      name: 'LatestFund',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'LatestFund'
      }
    },
    {
      path: '/PerformanceRanking',
      name: 'PerformanceRanking',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'PerformanceRanking'
      }
    },
    {
      path: '/ValuationRanking',
      name: 'ValuationRanking',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'ValuationRanking'
      }
    },
    {
      path: '/DebateFund',
      name: 'DebateFund',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'DebateFund'
      }
    },
    {
      path: '/StockFund',
      name: 'StockFund',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'non-DebateFund'
      }
    },
    {
      path: '/MyFund',
      name: 'MyFund',
      component: FundMarket,
      meta: {
        sorterOrFilter: 'MyFund'
      }
    },
    {
      path: '/SelfInfo',
      name: 'SelfInfo',
      component: SelfInfo,
    }
  ]
})

