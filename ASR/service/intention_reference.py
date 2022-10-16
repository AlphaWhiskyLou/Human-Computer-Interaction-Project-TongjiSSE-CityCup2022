import os
import joblib
import ahocorasick
import jieba
import numpy as np
# wangling
class IntentionReference:
    def __init__(self):
        cur_dir = '/'.join(os.path.abspath(__file__).split('/')[:-1])
        # 路径
        # self.vocab_path = os.path.join(cur_dir, 'data/bank_vocab.txt')
        self.stopwords_path =os.path.join(cur_dir, 'data/stop_words.utf8')
        # self.word2vec_path = os.path.join(cur_dir, 'data/merge_sgns_bigram_char300.txt')
        self.stopwords = [w.strip() for w in open(self.stopwords_path, 'r', encoding='utf8') if w.strip()]
        # 意图分类模型文件
        # self.tfidf_path = os.path.join(cur_dir, 'model/tfidf_model.m')
        # self.nb_path = os.path.join(cur_dir, 'model/intent_reg_model.m')  #朴素贝叶斯模型
        # self.tfidf_model = joblib.load(self.tfidf_path)
        # self.nb_model = joblib.load(self.nb_path)
        self.bank_path = os.path.join(cur_dir, 'data/bank_vocab.txt')
        self.bank_entities = [w.strip() for w in open(self.bank_path, encoding='utf8') if w.strip()]
        # 汇总领域词汇
        self.region_words = list(set(self.bank_entities))
        # 构造领域actree
        self.bank_tree = self.build_actree(list(set(self.bank_entities)))
        self.route_qwds = ['跳转', '转到', '弄到', '跳到', '我要到', '怎么到', '如何到', '去']
        self.function_qwds = ['功能', '什么功能', '哪些功能', '功能有哪些', '功能是什么']
        self.binding_qwds = ['绑定','什么绑定', '怎么绑定', '绑定儿子', '儿子帮我', '绑定子女', '绑定女儿', '绑定亲属', '绑定亲朋',
                             '女儿帮我', '小子帮我', '小伙帮我', '绑定姑娘']
        self.police_warning_qwds = ['打110', '报警', '救命', '救我', '被骗了', '诈骗', '完蛋了', '出事了', '警察', '杀人',
                                    '别杀我', 'help me']
    # 用ac自动机来构造领域的actree
    def build_actree(self, wordlist):
        actree = ahocorasick.Automaton()
        for index, word in enumerate(wordlist):
            actree.add_word(word, (index, word))
        actree.make_automaton()
        return actree
    def entity_reg(self, question):
        self.result = {}
        for i in self.bank_tree.iter(question):
            word = i[1][1]
            if "Bank" not in self.result:
                self.result["Bank"] = [word]
            else:
                self.result["Bank"].append(word)
            return self.result

    def check_words(self, wds, sent):
        for wd in wds:
            if wd in sent:
                return True
        return False
    # 当全匹配失败时，就采用相似度计算来找相似的词
    # 但是由于 1.word2vec的词向量太大了 2.内容相对较少 所以暂时不使用
    # def find_sim_words(self, question):
    #     import re
    #     import string
    #     from gensim.models import KeyedVectors
    #     jieba.load_userdict(self.vocab_path)
    #     self.model = KeyedVectors.load_word2vec_format(self.word2vec_path, binary=False)
    #     sentence = re.sub("[{}]", re.escape(string.punctuation), question)
    #     sentence = re.sub("[，。‘’；：？、！【】]", " ", sentence)
    #     sentence = sentence.strip()
    #     words = [w.strip() for w in jieba.cut(sentence) if
    #              w.strip() not in self.stopwords and len(w.strip()) >= 2]
    #     alist = []
    #     for word in words:
    #         temp = [self.bank_entities]
    #         for i in range(len(temp)):
    #             flag = ''
    #             if i == 0:
    #                 flag = "Bank"
    #             # elif i == 1:
    #             #     flag = ""
    #             # else:
    #             #     flag = ""
    #             scores = self.simCal(word, temp[i], flag)
    #             alist.extend(scores)
    #     temp1 = sorted(alist, key=lambda k: k[1], reverse=True)
    #     if temp1:
    #         self.result[temp1[0][2]] = [temp1[0][0]]

    # 采用DP方法计算编辑距离
    def editDistanceDP(self, s1, s2):
        m = len(s1)
        n = len(s2)
        solution = [[0 for j in range(n + 1)] for i in range(m + 1)]
        for i in range(len(s2) + 1):
            solution[0][i] = i
        for i in range(len(s1) + 1):
            solution[i][0] = i
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                if s1[i - 1] == s2[j - 1]:
                    solution[i][j] = solution[i - 1][j - 1]
                else:
                    solution[i][j] = 1 + min(solution[i][j - 1],
                                             min(solution[i - 1][j], solution[i - 1][j - 1]))
        return solution[m][n]
    # 计算词语和字典中的词的相似度
    def simCal(self, word, entities, flag):
        a = len(word)
        scores = []
        for entity in entities:
            sim_num = 0
            b = len(entity)
            c = len(set(entity+word))
            temp = []
            for w in word:
                if w in entity:
                    sim_num += 1
            if sim_num != 0:
                score1 = sim_num / c
                temp.append(score1)
            try:
                score2 = self.model.similarity(word, entity)  # 余弦相似度分数
                temp.append(score2)
            except:
                pass
            score3 = 1 - self.editDistanceDP(word, entity) / (a + b)  # 编辑距离分数
            if score3:
                temp.append(score3)
            score = sum(temp) / len(temp)
            if score >= 0.7:
                scores.append((entity, score, flag))
        scores.sort(key=lambda k: k[1], reverse=True)
        return scores
    # 提取问题的TF-IDF特征
    # 这个是词频特征，没有必要
    # def tfidf_features(self, text, vectorizer):
    #     jieba.load_userdict(self.vocab_path)
    #     words = [w.strip() for w in jieba.cut(text) if w.strip() and w.strip() not in self.stopwords]
    #     sents = [' '.join(words)]
    #     tfidf = vectorizer.transform(sents).toarray()
    #     return tfidf
    # 提取问题的关键词特征
    def other_features(self, text):
        features = [0] * 4
        for d in self.route_qwds:
            if d in text:
                features[0] += 1
        for s in self.function_qwds:
            if s in text:
                features[1] += 1
        for c in self.binding_qwds:
            if c in text:
                features[2] += 1
        for c in self.police_warning_qwds:
            if c in text:
                features[3] += 1
        m = max(features)
        n = min(features)
        normed_features = []
        if m == n:
            normed_features = features
        else:
            for i in features:
                j = (i - n) / (m - n)
                normed_features.append(j)
        return np.array(normed_features)
    # 预测意图
    # def model_predict(self, x, model):
    #     pred = model.predict(x)
    #     return pred
    # 意图推理主函数
    def make_reference(self, question):
        self.entity_reg(question)
        # # 如果没有直接找到相同的词汇，那么就去进行相似度计算
        # if not self.result:
        #     self.find_sim_words(question)
        types = []  # 实体类型
        for v in self.result.keys():
            types.append(v)
        intentions = []  # 查询意图
        # 意图预测
        # tfidf_feature = self.tfidf_features(question, self.tfidf_model)
        other_feature = self.other_features(question)
        m = other_feature.shape
        other_feature = np.reshape(other_feature, (1, m[0]))
        # feature = np.concatenate((tfidf_feature, other_feature), axis=1)
        # feature = np.concatenate((other_feature), axis=1)
        future = other_feature
        # predicted = self.model_predict(feature, self.nb_model)
        # intentions.append(predicted[0])
        # 跳转
        self.result['words'] = question
        if self.check_words(self.route_qwds, question) and ('Bank' in types):
            intention = "route"
            if intention not in intentions:
                intentions.append(intention)
        # 功能介绍
        if self.check_words(self.function_qwds, question) and ('Bank' in types):
            intention = "function"
            if intention not in intentions:
                intentions.append(intention)
        # 绑定
        if self.check_words(self.binding_qwds, question):
            intention = "binding_relatives"
            if intention not in intentions:
                intentions.append(intention)
        # 报警
        if self.check_words(self.police_warning_qwds, question):
            intention = "call_110"
            if intention not in intentions:
                intentions.append(intention)
        self.result["intentions"] = intentions
        return self.result