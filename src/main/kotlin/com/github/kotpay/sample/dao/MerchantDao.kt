package com.github.kotpay.sample.dao

import com.github.kotpay.sample.entity.Merchant
import org.springframework.data.repository.CrudRepository

interface MerchantDao : CrudRepository<Merchant, Long>