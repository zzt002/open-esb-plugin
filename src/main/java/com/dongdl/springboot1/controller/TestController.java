package com.dongdl.springboot1.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdl.springboot1.bean.TestBean;
import com.dongdl.springboot1.common.Enums.EsbServicePublishReqExampleEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.service.ITestMysqlService;
import com.dongdl.springboot1.service.ITestOracleService;
import com.dongdl.springboot1.service.ITestService;
import com.dongdl.springboot1.util.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description
 */
@RestController
@Api
@RequestMapping("/test")
@Slf4j
@Profile("test")
public class TestController {

    @Autowired
    private ITestOracleService testOracleService;
    @Autowired
    private ITestMysqlService testMysqlService;

//    @GetMapping("/list")
//    public ResultData list() {
//        return new ResultData(testMysqlService.list());
//    }

    @GetMapping("/listAll")
    public ResultData listAll() {
        return new ResultData(testMysqlService.listAll());
    }

    @GetMapping("/list")
    public ResultData list() {
        return new ResultData(testMysqlService.list());
    }
//
//    @GetMapping("/getOneByIp/{ip}")
//    public ResultData getOneByIp(@PathVariable String ip) {
//        return new ResultData(testMysqlService.getOneByIp(ip));
//    }
//
    @GetMapping("/saveOne")
    public ResultData saveOne(TestBean testBean) {
        testBean.setText(EsbServicePublishReqExampleEnum.CITY.getCode());
        return new ResultData(testMysqlService.saveOne(testBean));
    }


    @Autowired
    ITestService testService;

    @PostMapping("/test/saveOne")
    public ResultData test() {
        return new ResultData(testService.test());
    }

    @GetMapping("/savePath")
    public ResultData testPath() {
        return new ResultData(testMysqlService.savePath());
    }

    @GetMapping("/test")
    public ResultData test1(Integer seconds) {
//        log.info("进来一个{}, 参数{}", Thread.currentThread().getName(), seconds);
        if (null != seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ResultData("SUCCESS");
    }

    private static void me() {
        String id = "3574,3575,3576,3577,3578,3579,3580,3581,3582,3583,3584,3585,3586,3587,3588,3589,3590,3591,3592,3593,3594,3595,3596,3597,3598,3599,3600,3601,3602,3603,3604,3605,3606,3607,3608,3609,3610,3611,3612,3613,3614,3615,3616,3617,3618,3619,3620,3621,3622,3623,3624,3625,3626,3627,3628,3629,3630,3631,3632,3633,3634,3635,3636,3637,3638,3639,3640,3641,3642,3643,3644,3645,3646,3647,3648,3649,3650,3651,3652,3653,3654,3655,3656,3657,3658,3659,3660,3661,3662,3663,3664,3869,3665,3666,3667,3668,3669,3670,3671,3672,3673,3674,3675,3676,3677,3678,3679,3680,3683,3684,3685,3686,3687,3688,3689,3690,3691,3692,3693,3694,3695,3696,3697,3698,3699,3700,3701,3702,3703,3704,3705,3706,3707,3708,3709,3710,3711,3712,3713,3714,3715,3716,3717,3718,3719,3720,3721,3722,3723,3724,3725,3726,3727,3728,3729,3730,3731,3732,3733,3734,3735,3736,3737,3738,3739,3740,3741,3742,3743,3744,3745,3746,3747,3748,3749,3750,3751,3752,3753,3754,3755,3756,3757,3758,3759,3760,3761,3762,3763,3764,3765,3766,3767,3768,3769,3770,3771,3772,3773,3774,3775,3776,3777,3778,3779,3780,3781,3782,3783,3784,3785,3786,3787,3788,3789,3790,3791,3792,3793,3794,3795,3796,3797,3798,3799,3800,3801,3802,3803,3804,3805,3806,3807,3808,3809,3810,3811,3812,3813,3814,3815,3816,3817,3818,3819,3820,3821,3822,3823,3824,3825,3826,3827,3828,3829,3830,3831,3832,3833,3834,3835,3836,3837,3838,3839,3840,3841,3842,3843,3844,3845,3846,3847,3848,3849,3850,3851,3852,3853,3854,3855,3856,3857,3858,3859,3860,3861,3862,3863,3864,3865,3866,3867,3868,3461,3462,3463,3464,3465,3466,3467,3468,3469,3470,3471,3472,3473,3474,3475,3476,3477,3478,3479,3480,3481,3482,3483,3484,3485,3486,3487,3488,3489,3490,3491,3492,3493,3494,3495,3496,3497,3498,3499,3500,3501,3502,3503,3504,3505,3506,3507,3508,3509,3510,3511,3512,3513,3514,3515,3516,3517,3518,3519,3520,3521,3522,3523,3524,3525,3526,3527,3528,3529,3530,3531,3532,3533,3534,3535,3536,3537,3538,3539,3540,3541,3542,3543,3544,3545,3546,3547,3548,3549,3550,3551,3552,3553,3554,3555,3556,3557,3558,3559,3560,3561,3562,3563,3564,3565,3566,3567,3568,3569,3570,3571,3572,3573";
        String servcode = "33.1111.zj.province_9951.SynReq,33.1111.zj.province_9949.SynReq,33.1111.zj.province_9947.SynReq,33.1111.zj.province_9945.SynReq,33.1111.zj.province_9943.SynReq,33.1111.zj.province_9941.SynReq,33.1111.zj.province_9939.SynReq,33.1111.zj.province_9937.SynReq,33.1111.zj.province_9935.SynReq,33.1111.zj.province_9933.SynReq,33.1111.zj.province_9931.SynReq,33.1111.zj.province_9929.SynReq,33.1111.zj.province_9927.SynReq,33.1111.zj.province_9925.SynReq,33.1111.zj.province_9923.SynReq,33.1111.zj.province_9921.SynReq,33.1111.zj.province_9919.SynReq,33.1111.zj.province_9917.SynReq,33.1111.zj.province_9915.SynReq,33.1111.zj.province_9913.SynReq,33.1111.zj.province_9911.SynReq,33.1111.zj.province_9909.SynReq,33.1111.zj.province_9907.SynReq,33.1111.zj.province_9905.SynReq,33.1111.zj.province_9903.SynReq,33.1111.zj.province_9901.SynReq,33.1111.zj.province_9899.SynReq,33.1111.zj.province_9897.SynReq,33.1111.zj.province_9895.SynReq,33.1111.zj.province_9893.SynReq,33.1111.zj.province_9891.SynReq,33.1111.zj.province_9889.SynReq,33.1111.zj.province_9887.SynReq,33.1111.zj.province_9871.SynReq,33.1111.zj.province_9795.SynReq,33.1111.zj.province_9793.SynReq,33.1111.zj.province_9251.SynReq,33.1111.zj.province_9249.SynReq,33.1111.zj.province_9247.SynReq,33.1111.zj.province_9245.SynReq,33.1111.zj.province_9243.SynReq,33.1111.zj.province_9241.SynReq,33.1111.zj.province_9239.SynReq,33.1111.zj.province_9237.SynReq,33.1111.zj.province_9235.SynReq,33.1111.zj.province_9233.SynReq,33.1111.zj.province_9231.SynReq,33.1111.zj.province_9229.SynReq,33.1111.zj.province_9227.SynReq,33.1111.zj.province_9225.SynReq,33.1111.zj.province_9223.SynReq,33.1111.zj.province_9221.SynReq,33.1111.zj.province_9219.SynReq,33.1111.zj.province_9217.SynReq,33.1111.zj.province_9215.SynReq,33.1111.zj.province_9213.SynReq,33.1111.zj.province_9211.SynReq,33.1111.zj.province_9209.SynReq,33.1111.zj.province_9207.SynReq,33.1111.zj.province_9203.SynReq,33.1111.zj.province_9197.SynReq,33.1111.zj.province_9193.SynReq,33.1111.zj.province_9191.SynReq,33.1111.zj.province_9189.SynReq,33.1111.zj.province_9187.SynReq,33.1111.zj.province_9179.SynReq,33.1111.zj.province_9167.SynReq,33.1111.zj.province_9165.SynReq,33.1111.zj.province_8653.SynReq,33.1111.zj.province_8181.SynReq,33.1111.zj.province_11816.SynReq,33.1111.zj.province_11814.SynReq,33.1111.zj.province_11812.SynReq,33.1111.zj.province_11810.SynReq,33.1111.zj.province_11808.SynReq,33.1111.zj.province_11806.SynReq,33.1111.zj.province_11804.SynReq,33.1111.zj.province_11802.SynReq,33.1111.zj.province_11800.SynReq,33.1111.zj.province_11798.SynReq,33.1111.zj.province_11796.SynReq,33.1111.zj.province_11794.SynReq,33.1111.zj.province_11716.SynReq,33.1111.zj.province_11714.SynReq,33.1111.zj.province_11712.SynReq,33.1111.zj.province_11710.SynReq,33.1111.zj.province_11708.SynReq,33.1111.zj.province_11706.SynReq,33.1111.zj.province_11704.SynReq,33.1111.zj.province_11702.SynReq,33.1111.zj.province_11700.SynReq,33.1111.zj.province_11479.SynReq,33.1111.zj.province_11698.SynReq,33.1111.zj.province_11696.SynReq,33.1111.zj.province_11694.SynReq,33.1111.zj.province_11690.SynReq,33.1111.zj.province_11686.SynReq,33.1111.zj.province_11650.SynReq,33.1111.zj.province_11648.SynReq,33.1111.zj.province_11646.SynReq,33.1111.zj.province_11644.SynReq,33.1111.zj.province_11642.SynReq,33.1111.zj.province_11634.SynReq,33.1111.zj.province_11505.SynReq,33.1111.zj.province_11503.SynReq,33.1111.zj.province_11501.SynReq,33.1111.zj.province_11499.SynReq,33.1111.zj.province_11497.SynReq,33.1111.zj.province_11495.SynReq,33.1111.zj.province_11493.SynReq,33.1111.zj.province_11491.SynReq,33.1111.zj.province_11489.SynReq,33.1111.zj.province_11487.SynReq,33.1111.zj.province_11485.SynReq,33.1111.zj.province_11483.SynReq,33.1111.zj.province_11481.SynReq,33.1111.zj.province_11477.SynReq,33.1111.zj.province_11475.SynReq,33.1111.zj.province_11473.SynReq,33.1111.zj.province_11471.SynReq,33.1111.zj.province_11469.SynReq,33.1111.zj.province_11467.SynReq,33.1111.zj.province_11465.SynReq,33.1111.zj.province_11463.SynReq,33.1111.zj.province_11461.SynReq,33.1111.zj.province_11459.SynReq,33.1111.zj.province_11457.SynReq,33.1111.zj.province_11455.SynReq,33.1111.zj.province_11453.SynReq,33.1111.zj.province_11451.SynReq,33.1111.zj.province_10624.SynReq,33.1111.zj.province_10622.SynReq,33.1111.zj.province_10620.SynReq,33.1111.zj.province_10618.SynReq,33.1111.zj.province_10616.SynReq,33.1111.zj.province_10612.SynReq,33.1111.zj.province_10610.SynReq,33.1111.zj.province_10608.SynReq,33.1111.zj.province_10606.SynReq,33.1111.zj.province_10604.SynReq,33.1111.zj.province_10602.SynReq,33.1111.zj.province_10600.SynReq,33.1111.zj.province_10598.SynReq,33.1111.zj.province_10596.SynReq,33.1111.zj.province_10586.SynReq,33.1111.zj.province_10582.SynReq,33.1111.zj.province_10580.SynReq,33.1111.zj.province_10578.SynReq,33.1111.zj.province_10576.SynReq,33.1111.zj.province_10574.SynReq,33.1111.zj.province_10572.SynReq,33.1111.zj.province_10570.SynReq,33.1111.zj.province_10568.SynReq,33.1111.zj.province_10566.SynReq,33.1111.zj.province_10564.SynReq,33.1111.zj.province_10556.SynReq,33.1111.zj.province_10554.SynReq,33.1111.zj.province_10552.SynReq,33.1111.zj.province_10550.SynReq,33.1111.zj.province_10546.SynReq,33.1111.zj.province_10540.SynReq,33.1111.zj.province_10532.SynReq,33.1111.zj.province_10528.SynReq,33.1111.zj.province_10526.SynReq,33.1111.zj.province_10524.SynReq,33.1111.zj.province_10522.SynReq,33.1111.zj.province_10520.SynReq,33.1111.zj.province_10518.SynReq,33.1111.zj.province_10516.SynReq,33.1111.zj.province_10514.SynReq,33.1111.zj.province_10512.SynReq,33.1111.zj.province_10510.SynReq,33.1111.zj.province_10508.SynReq,33.1111.zj.province_10506.SynReq,33.1111.zj.province_10504.SynReq,33.1111.zj.province_10502.SynReq,33.1111.zj.province_10500.SynReq,33.1111.zj.province_10494.SynReq,33.1111.zj.province_10492.SynReq,33.1111.zj.province_10490.SynReq,33.1111.zj.province_10488.SynReq,33.1111.zj.province_10486.SynReq,33.1111.zj.province_10584.SynReq,33.1111.zj.province_10484.SynReq,33.1111.zj.province_10482.SynReq,33.1111.zj.province_10480.SynReq,33.1111.zj.province_10478.SynReq,33.1111.zj.province_10476.SynReq,33.1111.zj.province_10095.SynReq,33.1111.zj.province_10093.SynReq,33.1111.zj.province_10091.SynReq,33.1111.zj.province_10079.SynReq,33.1111.zj.province_10077.SynReq,33.1111.zj.province_10075.SynReq,33.1111.zj.province_10073.SynReq,33.1111.zj.province_10071.SynReq,33.1111.zj.province_10069.SynReq,33.1111.zj.province_10065.SynReq,33.1111.zj.province_10063.SynReq,33.1111.zj.province_10061.SynReq,33.1111.zj.province_10059.SynReq,33.1111.zj.province_10057.SynReq,33.1111.zj.province_10055.SynReq,33.1111.zj.province_10053.SynReq,33.1111.zj.province_10051.SynReq,33.1111.zj.province_10041.SynReq,33.1111.zj.province_10035.SynReq,33.1111.zj.province_10033.SynReq,33.1111.zj.province_10031.SynReq,33.1111.zj.province_10029.SynReq,33.1111.zj.province_10027.SynReq,33.1111.zj.province_10025.SynReq,33.1111.zj.province_10023.SynReq,33.1111.zj.province_10021.SynReq,33.1111.zj.province_10019.SynReq,33.1111.zj.province_10017.SynReq,33.1111.zj.province_10015.SynReq,33.1111.zj.province_10013.SynReq,33.1111.zj.province_10011.SynReq,33.1111.zj.province_10005.SynReq,33.1111.zj.province_10003.SynReq,33.1111.zj.province_12975.SynReq,33.1111.zj.province_12973.SynReq,33.1111.zj.province_12971.SynReq,33.1111.zj.province_12969.SynReq,33.1111.zj.province_12967.SynReq,33.1111.zj.province_12965.SynReq,33.1111.zj.province_12963.SynReq,33.1111.zj.province_12961.SynReq,33.1111.zj.province_12959.SynReq,33.1111.zj.province_12957.SynReq,33.1111.zj.province_12955.SynReq,33.1111.zj.province_12953.SynReq,33.1111.zj.province_12951.SynReq,33.1111.zj.province_12949.SynReq,33.1111.zj.province_12947.SynReq,33.1111.zj.province_12945.SynReq,33.1111.zj.province_12943.SynReq,33.1111.zj.province_12941.SynReq,33.1111.zj.province_12939.SynReq,33.1111.zj.province_12937.SynReq,33.1111.zj.province_12935.SynReq,33.1111.zj.province_12933.SynReq,33.1111.zj.province_12931.SynReq,33.1111.zj.province_12929.SynReq,33.1111.zj.province_12927.SynReq,33.1111.zj.province_12925.SynReq,33.1111.zj.province_12923.SynReq,33.1111.zj.province_12921.SynReq,33.1111.zj.province_12919.SynReq,33.1111.zj.province_12917.SynReq,33.1111.zj.province_12915.SynReq,33.1111.zj.province_12913.SynReq,33.1111.zj.province_12911.SynReq,33.1111.zj.province_12909.SynReq,33.1111.zj.province_12907.SynReq,33.1111.zj.province_12905.SynReq,33.1111.zj.province_12903.SynReq,33.1111.zj.province_12901.SynReq,33.1111.zj.province_12899.SynReq,33.1111.zj.province_12897.SynReq,33.1111.zj.province_12895.SynReq,33.1111.zj.province_12818.SynReq,33.1111.zj.province_12816.SynReq,33.1111.zj.province_12814.SynReq,33.1111.zj.province_12812.SynReq,33.1111.zj.province_12810.SynReq,33.1111.zj.province_12808.SynReq,33.1111.zj.province_12806.SynReq,33.1111.zj.province_12804.SynReq,33.1111.zj.province_12802.SynReq,33.1111.zj.province_12800.SynReq,33.1111.zj.province_12798.SynReq,33.1111.zj.province_12277.SynReq,33.1111.zj.province_12215.SynReq,33.1111.zj.province_12213.SynReq,33.1111.zj.province_12211.SynReq,33.1111.zj.province_12209.SynReq,33.1111.zj.province_12197.SynReq,33.1111.zj.province_12195.SynReq,33.1111.zj.province_12193.SynReq,33.1111.zj.province_12191.SynReq,33.1111.zj.province_12189.SynReq,33.1111.zj.province_12187.SynReq,33.1111.zj.province_12183.SynReq,33.1111.zj.province_12181.SynReq,33.1111.zj.province_12179.SynReq,33.1111.zj.province_12177.SynReq,33.1111.zj.province_12068.SynReq,33.1111.zj.province_12058.SynReq,33.1111.zj.province_12056.SynReq,33.1111.zj.province_12054.SynReq,33.1111.zj.province_12052.SynReq,33.1111.zj.province_12050.SynReq,33.1111.zj.province_12048.SynReq,33.1111.zj.province_12046.SynReq,33.1111.zj.province_12044.SynReq,33.1111.zj.province_12042.SynReq,33.1111.zj.province_12040.SynReq,33.1111.zj.province_12038.SynReq,33.1111.zj.province_12036.SynReq,33.1111.zj.province_12034.SynReq,33.1111.zj.province_12032.SynReq,33.1111.zj.province_12030.SynReq,33.1111.zj.province_12028.SynReq,33.1111.zj.province_12026.SynReq,33.1111.zj.province_12024.SynReq,33.1111.zj.province_12022.SynReq,33.1111.zj.province_12020.SynReq,33.1111.zj.province_12018.SynReq,33.1111.zj.province_12016.SynReq,33.1111.zj.province_12014.SynReq,33.1111.zj.province_12012.SynReq,33.1111.zj.province_12010.SynReq,33.1111.zj.province_12008.SynReq,33.1111.zj.province_12006.SynReq,33.1111.zj.province_12004.SynReq,33.1111.zj.province_12002.SynReq,33.1111.zj.province_12000.SynReq,33.1111.zj.province_11998.SynReq,33.1111.zj.province_11996.SynReq,33.1111.zj.province_11994.SynReq,33.1111.zj.province_11992.SynReq,33.1111.zj.province_11990.SynReq,33.1111.zj.province_11988.SynReq,33.1111.zj.province_11986.SynReq,33.1111.zj.province_11984.SynReq,33.1111.zj.province_11982.SynReq,33.1111.zj.province_11980.SynReq,33.1111.zj.province_11978.SynReq,33.1111.zj.province_11976.SynReq,33.1111.zj.province_11974.SynReq,33.1111.zj.province_11972.SynReq,33.1111.zj.province_11970.SynReq,33.1111.zj.province_11968.SynReq,33.1111.zj.province_11966.SynReq,33.1111.zj.province_11964.SynReq,33.1111.zj.province_11962.SynReq,33.1111.zj.province_11960.SynReq,33.1111.zj.province_11958.SynReq,33.1111.zj.province_11956.SynReq,33.1111.zj.province_11954.SynReq,33.1111.zj.province_11952.SynReq,33.1111.zj.province_11950.SynReq,33.1111.zj.province_11948.SynReq,33.1111.zj.province_11946.SynReq,33.1111.zj.province_11944.SynReq,33.1111.zj.province_11942.SynReq,33.1111.zj.province_11940.SynReq,33.1111.zj.province_11938.SynReq,33.1111.zj.province_11936.SynReq,33.1111.zj.province_11934.SynReq,33.1111.zj.province_11932.SynReq,33.1111.zj.province_11930.SynReq,33.1111.zj.province_11928.SynReq,33.1111.zj.province_11926.SynReq,33.1111.zj.province_11924.SynReq,33.1111.zj.province_11922.SynReq,33.1111.zj.province_11920.SynReq,33.1111.zj.province_11918.SynReq,33.1111.zj.province_11916.SynReq,33.1111.zj.province_11914.SynReq,33.1111.zj.province_11912.SynReq,33.1111.zj.province_11910.SynReq,33.1111.zj.province_11908.SynReq,33.1111.zj.province_11906.SynReq,33.1111.zj.province_11898.SynReq,33.1111.zj.province_11896.SynReq,33.1111.zj.province_11894.SynReq,33.1111.zj.province_11892.SynReq,33.1111.zj.province_11890.SynReq,33.1111.zj.province_11888.SynReq,33.1111.zj.province_11886.SynReq,33.1111.zj.province_11884.SynReq,33.1111.zj.province_11880.SynReq,33.1111.zj.province_11878.SynReq,33.1111.zj.province_11876.SynReq,33.1111.zj.province_11874.SynReq,33.1111.zj.province_11870.SynReq,33.1111.zj.province_11868.SynReq,33.1111.zj.province_11864.SynReq,33.1111.zj.province_11862.SynReq,33.1111.zj.province_11860.SynReq,33.1111.zj.province_11858.SynReq,33.1111.zj.province_11856.SynReq,33.1111.zj.province_11854.SynReq,33.1111.zj.province_11852.SynReq,33.1111.zj.province_11850.SynReq,33.1111.zj.province_11848.SynReq,33.1111.zj.province_11846.SynReq,33.1111.zj.province_11844.SynReq,33.1111.zj.province_11842.SynReq,33.1111.zj.province_11840.SynReq,33.1111.zj.province_11838.SynReq,33.1111.zj.province_11836.SynReq,33.1111.zj.province_11834.SynReq,33.1111.zj.province_11832.SynReq,33.1111.zj.province_11830.SynReq,33.1111.zj.province_11828.SynReq,33.1111.zj.province_11826.SynReq,33.1111.zj.province_11904.SynReq,33.1111.zj.province_11902.SynReq,33.1111.zj.province_11900.SynReq,33.1111.zj.province_11818.SynReq,33.1111.zj.province_10001.SynReq,33.1111.zj.province_9955.SynReq,33.1111.zj.province_9953.SynReq";

        String[] ids = id.split(",");
        String[] codes = servcode.split(",");

        String url = "http://172.18.106.107:8002/ESBAdmin/atomServiceAction!reload_server_by_inbound_main.ajax";
        String result= null;
        int s=0;
        ArrayList<String> idlist = Lists.newArrayList();
        ArrayList<String> codelist = Lists.newArrayList();
        for (int i = 0; i < 407; i++) {
            HashMap<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("ids", ids[i]);
            paramMap.put("servCodes", codes[i]);
            result = HttpUtil.doPostByCookie(url, paramMap, "JSESSIONID=a1v3rv3lnjodaei9pmbldmmv");
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!"ok".equals(jsonObject.get("state"))) {
                idlist.add(ids[i]);
                codelist.add(codes[i]);
                System.out.println(codes[i]);
                System.out.println(jsonObject.get("message"));
            } else {
                s++;
                System.out.println("s:" + s);
            }
        }
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("s", s);
        resultMap.put("ids", idlist);
        resultMap.put("codes", codelist);
//        return new ResultData(resultMap);
    }

}
