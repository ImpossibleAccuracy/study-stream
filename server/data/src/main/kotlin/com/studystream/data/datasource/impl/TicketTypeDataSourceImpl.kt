package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.TicketDao
import com.studystream.data.datasource.base.TicketTypeDataSource
import com.studystream.data.model.TicketTypeImpl
import com.studystream.domain.model.Ticket

class TicketTypeDataSourceImpl : TicketTypeDataSource,
    CrudDataSourceImpl<Ticket.Type, TicketTypeImpl>(TicketDao.TypeDao)