# JMS Migration - Remaining Listeners

All listeners use @JmsListener (Spring). Remaining queue handlers to migrate to JmsTemplate:
- [x] ServiceMultiEditListener
- [x] MultiMacdListener (in progress)
- [ ] QuoteProcessingListener
- [ ] QuoteProcessingFailureListener
- [ ] LocationMessageListener
- [ ] InvoiceChargeQueueListener
- [ ] IntervalQueueListener
- [ ] FileImportQueueListener
- [ ] MultiDisputeListener
- [ ] DisputeMultiEditListener
- [ ] DisconnectMultiEditListener
- [ ] CompanyMessageListener

Queue handlers to migrate to JmsTemplate:
- [x] ServiceMultiEditQueueHandler
- [x] MultiMacdQueueHandler
- [ ] CompanyMessageHandler
- [ ] DisconnectMultiEditQueueHandler
- [ ] DisputeMultiEditQueueHandler
- [ ] MultiDisputeQueueHandler
- [ ] InvoiceChargeQueueHandler
- [ ] IntervalQueueHandler
- [ ] LocationMessageHandler
- [ ] (FileImport - check)
