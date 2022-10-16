import Vue from 'vue'
import Router from 'vue-router'
import utilityManagement from '../components/UtilityFunction/utilityManagement'
import assistanceManagement from '../components/assistanceFunction/assistanceManagement'
import processAssistance from "../components/assistanceFunction/processAssistance";
import initAssistance from "../components/assistanceFunction/initAssistance";
import viewAssistance from "../components/assistanceFunction/viewAssistance";
import utilityRecord from "../components/utilityFunction/utilityRecord";
import payUtilityFee from "../components/utilityFunction/payUtilityFee";
import Home from "../components/Home";
import Corefunc from "../components/coreFunction/Corefunc";
import Record from "../components/coreFunction/Record";
import conciergePlaceHolder from "../components/conciergeService/conciergePlaceHolder";
import viewConcierge from "../components/conciergeService/viewConcierge";
import initTransferAssistance from "../components/assistanceFunction/initTransferAssistance";
import newUtilityFee from "../components/utilityFunction/newUtilityFee";
import newInitAssistance from "../components/assistanceFunction/newInitAssistance";
import initAssistance2 from "../components/assistanceFunction/initAssistance2";
import Login from "../components/loginFunction/Login";
import Register from "../components/loginFunction/Register";
import initAssistance3 from "../components/assistanceFunction/initAssistance3";
import VueRouter from "vue-router";
import openAccount from "../components/coreFunction/openAccount";
import Unity from "../components/loginFunction/Unity";
import RealViewConcierge from "../components/conciergeService/RealViewConcierge";
import PostCard from "../components/PostCard";
import Introduction from "../components/Introduction"

Vue.use(Router)


const routes = [{
        path: '/',
        redirect: '/login',
        hidden: true
    },
    {
        path: '/login',
        name: '登录',
        hidden: true,
        component: Login
    }, {
        path: '/register',
        name: '注册',
        hidden: true,
        component: Register
    }, {
        path: '/unity',
        name: '关联',
        hidden: true,
        component: Unity
    },
    {
        path: '/home',
        name: '',
        component: Home,
        hidden: true
    }, {
        path: '/home',
        name: '网站介绍',
        component: Home,
        iconCls: 'el-icon-s-management',
        children: [{
            path: '/introduction',
            name: '业务概览',
            component: Introduction,
            meta: {
                keepAlive: true,
            }
        }]
    },
    {
        path: '/home',
        name: '网上银行',
        component: Home,
        iconCls: 'el-icon-s-finance',
        children: [{
            path: '/coreFunction',
            name: '基本功能',
            component: Corefunc,
            meta: {
                keepAlive: true,
            }
        }, {
            path: '/record',
            name: '交易记录查询',
            component: Record,
            meta: {
                keepAlive: true
            }
        }, {
            path: '/openAccount',
            name: '网上开户',
            component: openAccount,
            meta: {
                keepAlive: true
            }
        }]
    },
    {
        path: '/home',
        component: Home,
        name: '生活缴费',
        iconCls: 'el-icon-coin',
        children: [{
            path: '/utilityManagement',
            name: '生活缴费',
            component: utilityManagement,
            hidden: true,
            meta: {
                keepAlive: true
            }
        }, {
            path: '/utilityRecord',
            name: '生活缴费记录',
            component: utilityRecord,
            hidden: true,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/payUtilityFee',
            name: '旧生活费用缴纳',
            component: payUtilityFee,
            hidden: true,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/newUtilityFee',
            name: '生活费用缴纳',
            component: newUtilityFee,
            meta: {
                keepAlive: false
            }
        }]
    },
    {
        path: '/home',
        name: '亲属协助',
        component: Home,
        iconCls: 'el-icon-connection',
        children: [{
            path: '/assistanceManagement',
            name: '辅助功能管理',
            component: assistanceManagement,
            hidden: true,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/processAssistance',
            name: '处理已发起的亲属协助',
            component: processAssistance,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/initAssistance',
            name: '发起亲属协助',
            component: initAssistance,
            hidden: true,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/initAssistance2',
            name: '发起亲属协助',
            component: initAssistance2,
            hidden: true,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/initAssistance3',
            name: '发起亲属协助',
            component: initAssistance3,
            hidden: true,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/newInitAssistance',
            name: '发起亲属协助',
            component: newInitAssistance,
            meta: {
                keepAlive: false
            }
        }, {
            path: '/viewAssistance',
            name: '查看亲属协助记录',
            component: viewAssistance,
            meta: {
                keepAlive: false
            }
        }]
    },
    // {
    //   path: '/home',
    //   name: '上门服务',
    //   component: Home,
    //   iconCls: 'el-icon-location',
    //   children: [{
    //     path: '/concierge',
    //     name: '礼宾服务',
    //     component: conciergePlaceHolder,
    //     meta: {
    //       keepAlive: true
    //     }
    //   },{
    //     path: '/viewconcierge',
    //     name: '查询已预约的礼宾服务',
    //     component: RealViewConcierge,
    //     meta: {
    //       keepAlive: false
    //     }
    // }]
    // }
];
const router = new Router({
        mode: "history",
        routes,
    })
    // router.beforeEach((to, from, next) =>{
    //   const token = localStorage.getItem('token')
    //   if(to.name !=='/' && !token) next({to:'/'})
    //   else next()
    // })

router.beforeEach((to, from, next) => {
    console.log('路由拦截')
    if (to.path === '/login') {
        console.log('登录')
        next();
    } else {
        if (localStorage.getItem('token') != null) {
            console.log('已登录')
            next();
        } else {
            console.log('未登录')
            next({ path: '/login' });
        }
    }
})
export default router