getPolityById
===
* 一个简单的查询例子
	
	select id, name, status from polity where 1=1
	@if ( !isEmpty (id) ) { 
		and id = #id# 
	@}

getPolityByStatus
===
* 一个简单的查询例子

	select id, name, status from polity where 1=1
	@if ( !isEmpty (status) ) { 
		and status = #status# 
	@}