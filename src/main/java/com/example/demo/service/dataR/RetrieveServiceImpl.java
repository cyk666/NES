package com.example.demo.service.dataR;

import com.example.demo.domain.Node;
import com.example.demo.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cyk
 * 数据检索的方法在这里实现。
 */
@Service
public class RetrieveServiceImpl implements RetrieveService {

    @Autowired
    NodeRepository nodeRepository;

    //实现测试方法，在Service层调用NodeRepository中定义的 findByIp
    public Node findByIp(String ip) {
        return nodeRepository.findByIp(ip);
    }
}
