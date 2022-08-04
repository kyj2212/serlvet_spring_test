<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../common/head_withEditor.jspf"%>
	<!--body class="bg-white font-serif"-->



		<div class="h-screen w-full px-6 md:px-12 pb-12 pt-32 flex flex-wrap justify-between">

			<div class="bg-gray-lighter md:h-full w-full md:flex-1 bg-cover bg-center bg-no-repeat" style="background-image: url('https://cdn.pixabay.com/photo/2018/04/19/21/17/lion-3334357_960_720.jpg')"></div>

			<div class="bg-orange-100 text-orange-900 md:h-full w-full md:flex-1 flex justify-center items-center">
				<div class="px-8 md:px-16">


					<h1 class="text-lg md:text-3xl mb-6">${article.title}</h1>
					<p class="mb-6"><span class="font-bold">by 예진</span> | <span class="opacity-75 ">${article.createdDate}</span></p> <!-- //line-through -->

					<a href="#" onclick="location.href='/usr/article/modify/free/${article.id}'" class="block md:inline-block text-center no-underline text-orange-900 px-5 py-3 border-2 border-orange-900 hover:bg-orange-900 hover:text-orange-100">게시글 수정</a>
					<a href="#" onclick="location.href='/usr/article/delete/free/${article.id}'" class="block md:inline-block text-center no-underline text-orange-900 px-5 py-3 border-2 border-orange-900 hover:bg-orange-900 hover:text-orange-100">게시글 삭제</a>
				</div>
			</div>

		</div>

		<div class="p-12" id="more">

			<h2 class="text-2xl md:text-4xl max-w-2xl mx-auto text-center text-gray-900 leading-normal mb-12">${article.title}</h2>

			<!--p class="text-sm md:text-base leading-normal max-w-xl text-center mx-auto text-gray-800 mb-8">${article.id}</p-->
			<div class="text-sm md:text-base leading-normal max-w-xl text-center mx-auto text-gray-800">
                    <div class="toast-ui-viewer">
                        <script type="text/x-template"> ${article.body} </script>
                    </div>
            </div>
			<!--p class="text-sm md:text-base leading-normal max-w-xl text-center mx-auto text-gray-800">${article.body}</p-->
			<p class="mt-10 text-sm md:text-base leading-normal max-w-xl text-right mx-auto text-gray-800">${article.createdDate} | ${article.modifiedDate}</p>
				<div class="px-8 md:px-16 text-right mb-10">
			<a href="#" onclick="location.href='/usr/article/list/free'" class="block md:inline-block text-center no-underline text-orange-900 px-5 py-3 border-2 border-orange-900 hover:bg-orange-900 hover:text-orange-100">자유게시판 목록</a>
				</div>
		</div>






	</body>


<%@ include file="../common/foot.jspf"%>