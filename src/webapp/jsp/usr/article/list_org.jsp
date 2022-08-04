<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


		<!-- blog -->
		<div class="w-full bg-white">

			<!-- title -->
			<div class="text-center px-6 py-12 mb-6 bg-gray-100 border-b">
				<h1 class=" text-xl md:text-4xl pb-4">Articles</h1>
				<p class="leading-loose text-gray-dark">
					여기는 게시글 목록입니다
				</p>
			</div>
			<!-- /title -->


			<div class="container max-w-4xl mx-auto md:flex items-start py-8 px-12 md:px-0">
				<!-- articles -->
				<div class="w-full md:pr-12 mb-12">

                    <!--%for( ArticleDto article : articles) {%-->
                    <c:forEach items="${articles}" var="article">
					<article class="mb-12">
						<h2 class="mb-4">
							<a href="/usr/article/detail/free/${article.id}" class="text-black text-xl md:text-2xl no-underline hover:underline">
								${article.title}
							</a>
						</h2>
						<div class="mb-4 text-sm text-gray-700">
							by <a href="#" class="text-gray-700"> ${article.writer}
							<span class="font-bold mx-1"> | </span>
							<a href="#" class="text-gray-700">on ${article.createdDate} </a>
							<!--span class="font-bold mx-1"> | </span-->
							<!--a href="#" class="text-gray-700">0 Comments</a-->
						</div>
						<p class="text-gray-700 leading-normal">
							${article.body}
						</p>
					</article>
                    </c:forEach>
                    <article class="place-latest mb-12"></article>

                     <!--/ articles -->

                     <button class="block mb-4 px-3 py-2 text-xs font-bold rounded-full no-underline hover:shadow bg-blue-600 text-white"
                     onclick="location.href='/usr/article/write/free'">자유게시판 글쓰기</button>

                    <!-- ajax 로 최신글 가져오기 -->
                    <!--div class="px-0 py-3 mb-6 border-t border-b">
                    <script>
                        //console.log('1');
                        function Article__loadLatest(){
                            //console.log('2');
                            fetch('/usr/article/getArticles/free')
                                .then((response)=>response.json()) // json 파싱하는 역할
                                .then((responseData) => {
                                   // console.log('3');
                                    const articleDtoList = responseData.data;
                                    const latestArticle = articleDtoList[articleDtoList.length -1];
                                    const content = new Date() +" : " + latestArticle.title + "<br />";
                                    $('.place-latest').empty().prepend(content);
                                    $('.place-1').append(new Date() +" : "+ latestArticle.body +"<br />");  // append 는 뒤에 //prepend 는 앞에 //empty()는 비우기
                                    //console.log(data);
                                    });
                          //  console.log('4');// fetch를 하면 일단 응답이 오기 전에 url로 실행(?)부터 하고 응답이 오면 then()을 실행한다.
                        }

                    </script>
                        <button onclick="Article__loadLatest();" class="btn btn-sm mb-3">최신글 가져오기</button>
                        <div class="place-1"></div>
                    </div-->


			    </div>

            </div>
        </div>

<%@ include file="../common/foot.jspf"%>