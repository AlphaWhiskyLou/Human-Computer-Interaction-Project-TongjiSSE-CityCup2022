import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

//创建vuex实例
const store=new Vuex.Store({
    state:{
        token:'',
        userName:'',
        userId:null,
        status:0,
        role:0,
        creditCard:[]
    },
    mutations:{
        update(state,params){
            state.token=params
        },
        save(state,params){
            state.userName=params
        },
        insert(state,params){
            state.userId=params
        },
        status(state,params){
            state.status=params
        },
        role(state,params){
            state.status=params
        },
        addCreditCard(state,params){
            state.creditCard=params
        }
    },
    actions:{
        updateToken(context,params){
            context.commit("update",params)
        },
        updateUserName(context,params){
            context.commit("save",params)
        },
        updateUserId(context,params){
            context.commit("insert",params)
        },
        updateStatus(context,params){
            context.commit("status",params)
        },
        updateRole(context,params){
            context.commit("role",params)
        },
        updateCreditCard(context,params){
            context.commit("addCreditCard",params)
        }
    }
})
export default store // 导出store