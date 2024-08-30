package com.studystream.di.utils

import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.Kind
import org.koin.core.definition.KoinDefinition
import org.koin.core.instance.SingleInstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier._q
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

inline fun <reified T : Any> Module.singlePropertiesOf(obj: T) {
    T::class.memberProperties.forEach { kProperty ->
        val kClass = kProperty.returnType.jvmErasure

        singleWithClass(kClass) {
            kProperty.get(obj)
        }
    }
}

@OptIn(KoinInternalApi::class)
fun Module.singleWithClass(
    kClass: KClass<*>,
    qualifier: Qualifier? = null,
    createdAtStart: Boolean = false,
    definition: Definition<*>
): KoinDefinition<*> {
    val def = BeanDefinition(
        _q("_root_"),
        kClass,
        qualifier,
        definition,
        Kind.Singleton,
    )

    val factory = SingleInstanceFactory(def)
    indexPrimaryType(factory)

    if (createdAtStart) {
        prepareForCreationAtStart(factory)
    }

    return KoinDefinition(this, factory)
}
