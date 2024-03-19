package restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restaurant.repository.JobAppRepository;
import restaurant.service.JobAppService;

@Service
@RequiredArgsConstructor
public class JobAppServiceImpl implements JobAppService {
    private final JobAppRepository jobAppRepository;

}
